package pl.droidsonroids.gradle.ui.test

open class UiTestExtension {
    var taskNamePattern: String? = null

    fun uiTestReporter(taskNamePattern: String) {
        this.taskNamePattern = taskNamePattern
    }
}