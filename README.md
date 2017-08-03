# android-gradle-ui-test-plugin
Gradle plugin for UI tests in Android projects

## Current version
See [Gradle plugin portal](https://plugins.gradle.org/plugin/pl.droidsonroids.ui.test)

## Features
- ADB connection timeout increased to 30s
- Ability to disable pre dexing (if appropriate property is set), useful on CI servers.
 [More info](http://www.littlerobots.nl/blog/disable-android-pre-dexing-on-ci-builds/).
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
```

### Available tasks
- `connectedUiTest` - setup, testing with spoon and revert to default settings 
- `connectedSetupUiTest` - setup only 
- `spoon` - testing only 
- `connectedSetupRevertUiTest` - setup revert only 