package pl.droidsonroids.gradle.ui.test

import com.android.build.gradle.TestedExtension
import com.android.ddmlib.AndroidDebugBridge
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class DeviceActionTask : DefaultTask() {

    @Internal
    private lateinit var bridge: AndroidDebugBridge

    init {
        group = "verification"
        AndroidDebugBridge.initIfNeeded(false)
    }

    @Input
    fun testedExtension(android: TestedExtension) {
        bridge = AndroidDebugBridge.createBridge(android.adbExecutable.path, false) ?: throw GradleException("Could not create AndroidDebugBridge")
    }

    protected abstract val worker: DeviceWorker

    @TaskAction
    fun performAction() {
        if (bridge.devices.isEmpty()) {
            throw GradleException("No connected devices")
        }

        bridge.devices.forEach {
            project.logger.info("Preparing ${it.name}")
            worker.doWork(it)
        }
    }

}