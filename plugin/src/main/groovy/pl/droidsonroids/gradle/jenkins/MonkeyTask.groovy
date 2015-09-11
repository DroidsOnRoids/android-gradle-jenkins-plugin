package pl.droidsonroids.gradle.jenkins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.LoggerWrapper
import com.android.builder.testing.ConnectedDeviceProvider
import com.android.ddmlib.ShellCommandUnresponsiveException
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

import java.util.concurrent.TimeUnit

class MonkeyTask extends DefaultTask {

    Project subproject
    Set<ApplicationVariant> applicationVariants
    File monkeyOutputFile

    {
        group = 'verification'
        description = 'Runs monkey application exerciser on all connected devices and/or emulators'
    }

    @TaskAction
    def connectedMonkeyTest() {
        def adbExe = subproject.extensions.getByType(AppExtension).adbExe
        def connectedDeviceProvider = new ConnectedDeviceProvider(adbExe, new LoggerWrapper(subproject.logger))
        connectedDeviceProvider.init()
        applicationVariants.each {
            variant ->
                def command = 'monkey -v --ignore-crashes --ignore-timeouts --ignore-security-exceptions --monitor-native-crashes --ignore-native-crashes -p ' + variant.applicationId + ' 1000'
                connectedDeviceProvider.getDevices().findAll {
                    it.apiLevel >= variant.mergedFlavor.minSdkVersion.apiLevel
                }.each { device ->
                    def deviceName = device.getName()
                    try {
                        subproject.logger.lifecycle('Monkeying on {}', deviceName)
                        device.executeShellCommand(command, new MonkeyOutputReceiver(monkeyOutputFile), 20, TimeUnit.SECONDS)
                    } catch (ShellCommandUnresponsiveException ex) {
                        subproject.logger.log(LogLevel.ERROR, 'Monkey timeout on device ' + deviceName, ex)
                        throw ex
                    }
                }
        }
        connectedDeviceProvider.terminate()
    }
}