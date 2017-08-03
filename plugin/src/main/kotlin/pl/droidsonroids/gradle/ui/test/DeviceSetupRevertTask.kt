package pl.droidsonroids.gradle.ui.test

import org.gradle.api.tasks.Internal

open class DeviceSetupRevertTask : DeviceActionTask() {
    init {
        description = "Reverts device setup after instrumentation tests"
    }

    @Internal
    override val worker = DeviceSetupReverter()
}
