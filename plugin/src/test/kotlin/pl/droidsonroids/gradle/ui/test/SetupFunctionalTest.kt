package pl.droidsonroids.gradle.ui.test

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SetupFunctionalTest {
    @get:Rule
    val temporaryFolder = TemporaryProjectFolder()

    @Before
    fun setUp() {
        temporaryFolder.newFolder("src", "main")
        temporaryFolder.copyResource("AndroidManifest.xml", "src/main/AndroidManifest.xml")
    }

    @Test
    fun `setup fails when there is no connected devices`() {
        temporaryFolder.copyResource("simple.gradle", "build.gradle")

        val result = GradleRunner
                .create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments(CONNECTED_SETUP_UI_TEST_TASK_NAME)
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()
        assertThat(result.output).contains("No connected devices")
    }

    @Test
    fun `setup invoked as dependent task in simple project`() {
        temporaryFolder.copyResource("simple.gradle", "build.gradle")

        val result = GradleRunner
                .create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedDebugUiTest", "-m")
                .withPluginClasspath()
                .withJaCoCo()
                .build()
        assertThat(result.output).contains(":$CONNECTED_SETUP_UI_TEST_TASK_NAME")
        assertThat(result.output).contains(":testDebugComposer")
        assertThat(result.output).contains(":$CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME")
    }

    @Test
    fun `setup invoked as dependent task in multi flavored project`() {
        temporaryFolder.copyResource("flavors.gradle", "build.gradle")

        val result = GradleRunner
                .create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedFreeDebugUiTest", "-m")
                .withPluginClasspath()
                .withJaCoCo()
                .build()
        assertThat(result.output).contains(":$CONNECTED_SETUP_UI_TEST_TASK_NAME")
        assertThat(result.output).contains(":testFreeDebugComposer")
        assertThat(result.output).contains(":$CONNECTED_SETUP_REVERT_UI_TEST_TASK_NAME")
    }
}
