package pl.droidsonroids.gradle.ui.test

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.Project
import pl.droidsonroids.gradle.ui.test.Constants.CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME
import pl.droidsonroids.gradle.ui.test.Constants.CONNECTED_SETUP_UI_TEST_TASK_NAME
import java.io.File
import java.io.InputStream
import java.util.*

inline fun <reified T : BaseExtension> Project.getAndroidExtension(): T =
        extensions.getByType(T::class.java)

val connectedAndroidTestTaskNamePattern = "connected%sAndroidTest"
val spoonTaskNamePattern = "spoon%sAndroidTest"
val connectedUiTestRegex = "connected(\\w*)UiTest".toRegex()

fun Project.configureUiTests(android: TestedExtension) {
    val deviceSetupTask = tasks.create(CONNECTED_SETUP_UI_TEST_TASK_NAME, DeviceSetupTask::class.java, {
        it.testedExtension(android)
    })

    val deviceSetupRevertTask = tasks.create(CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME, DeviceSetupRevertTask::class.java, {
        it.testedExtension(android)
    })

    afterEvaluate {
        val isSplitEnabled = android.splits.let { it.abi.isEnable || it.density.isEnable || it.language.isEnable }
        val testTaskNamePattern: String
        if (isSplitEnabled) {
            testTaskNamePattern = connectedAndroidTestTaskNamePattern
        } else {
            apply(mapOf("plugin" to "spoon"))
            testTaskNamePattern = spoonTaskNamePattern
        }

        tasks.addRule("Pattern: connected<ID>UiTest", { taskName ->
            val matchResult = connectedUiTestRegex.matchEntire(taskName)
            if (matchResult != null) {
                val variantName = matchResult.groupValues[1]
                it.description = "Setups connected devices and performs instrumentation tests for $variantName}"
                val testTaskName = when (variantName.isEmpty() && !isSplitEnabled) {
                    false -> testTaskNamePattern.format(Locale.ROOT, variantName)
                    true -> "spoon"
                }
                val testTask = tasks.getByName(testTaskName)
                testTask.mustRunAfter(deviceSetupTask)
                tasks.create(taskName, { task ->
                    task.group = "verification"
                    task.description = it.description
                    task.dependsOn(testTask, deviceSetupTask)
                    task.finalizedBy(deviceSetupRevertTask)
                })
            }
        })
    }
}

fun InputStream.toFile(file: File) {
    use { input ->
        file.outputStream().use { input.copyTo(it) }
    }
}
