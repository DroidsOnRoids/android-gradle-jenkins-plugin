package pl.droidsonroids.gradle.ui.test

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner

class ConnectedUiTestFunctionalTest {

    @get:Rule
    val temporaryFolder = TemporaryProjectFolder()

    @Test
    fun `simple build fails when no connected devices`() {
        temporaryFolder.copyResource("simple.gradle", "build.gradle")
        temporaryFolder.newFolder("src", "main")
        temporaryFolder.copyResource("AndroidManifest.xml", "src/main/AndroidManifest.xml")

        val result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedDebugUiTest")
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()

        assertThat(result.output).contains("No connected devices")
    }

    @Test
    fun `flavored build fails when no connected devices`() {
        temporaryFolder.copyResource("flavors.gradle", "build.gradle")
        temporaryFolder.newFolder("src", "main")
        temporaryFolder.copyResource("AndroidManifest.xml", "src/main/AndroidManifest.xml")

        val result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedProDebugUiTest")
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()

        assertThat(result.output).contains("No connected devices")
    }

    @Test
    fun `flavored multiple build fails when no connected devices`() {
        temporaryFolder.copyResource("flavors.gradle", "build.gradle")
        temporaryFolder.newFolder("src", "main")
        temporaryFolder.copyResource("AndroidManifest.xml", "src/main/AndroidManifest.xml")

        val result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedUiTest")
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()

        assertThat(result.output).contains("No connected devices")
    }

    @Test
    fun `split build fails when no connected devices`() {
        temporaryFolder.copyResource("splits.gradle", "build.gradle")
        temporaryFolder.newFolder("src", "main")
        temporaryFolder.copyResource("AndroidManifest.xml", "src/main/AndroidManifest.xml")

        val result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedDebugUiTest")
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()

        assertThat(result.output).contains("No connected devices")
    }

    @Test
    fun `library build fails when no connected devices`() {
        temporaryFolder.copyResource("library.gradle", "build.gradle")
        temporaryFolder.newFolder("src", "main")
        temporaryFolder.copyResource("AndroidManifest.xml", "src/main/AndroidManifest.xml")

        val result = GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withArguments("connectedDebugUiTest")
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()

        assertThat(result.output).contains("No connected devices")
    }

}