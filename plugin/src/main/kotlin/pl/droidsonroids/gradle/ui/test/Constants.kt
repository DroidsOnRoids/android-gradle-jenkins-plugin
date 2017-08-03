package pl.droidsonroids.gradle.ui.test

internal object Constants {

    val ADB_COMMAND_TIMEOUT_MILLIS = 30000

    val CONNECTED_UI_TEST_TASK_NAME = "connectedUiTest"
    val SPOON_TASK_NAME = "spoon"
    val CONNECTED_SETUP_UI_TEST_TASK_NAME = "connectedSetupUiTest"
    val CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME = "connectedSetupRevertUiTest"

    val MEDIA_SCAN_COMMAND = "am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://"
}