# android-gradle-ui-test-plugin
Gradle plugin for UI tests in Android projects

## Current version
See [Gradle plugin portal](https://plugins.gradle.org/plugin/pl.droidsonroids.ui.test)

## Features
- ADB connection timeout increased to 30s
- [spoon Gradle plugin](https://github.com/stanfy/spoon-gradle-plugin) integrated
- Preparation of connected device/AVD to instrumented (e.g. using espresso) tests - unlocking screen, disabling animations,
 adding sample media files to multimedia provider

## Usage
### Applying the plugin
```groovy
plugins {
  id 'pl.droidsonroids.ui.test' version '1.0.0'
}

apply plugin: 'com.android.application'

uiTest {
    uiTestReporter 'test%sComposer'
}
```

`uiTestReporter` takes test task pattern as an argument. It may be for example `'test%sComposer'`
if you are using [composer](https://github.com/trevjonez/composer-gradle-plugin) 
or `'spoon%s'` for [spoon](https://github.com/stanfy/spoon-gradle-plugin).

### Available tasks
- `connectedUiTest` - setup, testing with spoon and revert to default settings 
- `connectedSetupUiTest` - setup only 
- `connectedSetupRevertUiTest` - setup revert only 