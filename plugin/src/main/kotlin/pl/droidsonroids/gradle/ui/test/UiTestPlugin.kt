package pl.droidsonroids.gradle.ui.test

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.ddmlib.DdmPreferences
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.util.GradleVersion

class UiTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        checkGradleVersion()

        DdmPreferences.setTimeOut(ADB_COMMAND_TIMEOUT_MILLIS)

        project.allprojects { subproject ->
            subproject.pluginManager.apply(BasePlugin::class.java)

            subproject.plugins.withType(AppPlugin::class.java) {
                val android = subproject.getAndroidExtension<AppExtension>()
                subproject.configureUiTests(android)
            }
            subproject.plugins.withType(LibraryPlugin::class.java) {
                val android = subproject.getAndroidExtension<LibraryExtension>()
                subproject.configureUiTests(android)
            }
        }
    }

    private fun checkGradleVersion() {
        val minimumSupportedGradleVersion = GradleVersion.version("4.1")
        if (GradleVersion.current() < minimumSupportedGradleVersion) {
            throw GradleException("Gradle version ${GradleVersion.current().version} is not supported. Use Gradle Wrapper or Gradle version >= ${minimumSupportedGradleVersion.version}")
        }
    }
}

