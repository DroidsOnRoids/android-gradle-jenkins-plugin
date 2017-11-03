package pl.droidsonroids.gradle.ui.test

const val ADB_COMMAND_TIMEOUT_MILLIS = 30000

const val CONNECTED_UI_TEST_TASK_NAME_PATTERN = "connected%sUiTest"
const val CONNECTED_ANDROID_TASK_NAME_PATTERN = "connected%sAndroidTest"
const val CONNECTED_SETUP_UI_TEST_TASK_NAME = "connectedSetupUiTest"
const val CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME = "connectedSetupRevertUiTest"

const val MEDIA_SCAN_COMMAND = "am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://"
