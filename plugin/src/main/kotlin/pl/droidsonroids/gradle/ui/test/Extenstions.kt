package pl.droidsonroids.gradle.ui.test

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.Project
import java.io.File
import java.io.InputStream

inline fun <reified T : BaseExtension> Project.getAndroidExtension(): T =
        extensions.getByType(T::class.java)

val connectedUiTestRegex = "connected(\\w*)UiTest".toRegex()

fun Project.configureUiTests(android: TestedExtension) {
    val uiTestExtension = extensions.create("uiTest", UiTestExtension::class.java)

    val deviceSetupTask = tasks.create(CONNECTED_SETUP_UI_TEST_TASK_NAME, DeviceSetupTask::class.java, {
        it.testedExtension(android)
    })

    val deviceSetupRevertTask = tasks.create(CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME, DeviceSetupRevertTask::class.java, {
        it.testedExtension(android)
    })

    afterEvaluate {
        val dependentTaskPattern = uiTestExtension.taskNamePattern ?: CONNECTED_ANDROID_TASK_NAME_PATTERN
        tasks.addRule("Pattern: connected<ID>UiTest", { taskName ->
            val matchResult = connectedUiTestRegex.matchEntire(taskName)
            val variantName = matchResult?.groupValues?.get(1)
            if (!variantName.isNullOrEmpty()) {
                it.description = "Setups connected devices and performs instrumentation tests for $variantName}"
                val testTask = tasks.getByName(dependentTaskPattern.format(variantName))
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
