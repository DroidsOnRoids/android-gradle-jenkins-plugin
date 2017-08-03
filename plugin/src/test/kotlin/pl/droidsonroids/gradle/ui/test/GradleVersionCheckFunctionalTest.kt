package pl.droidsonroids.gradle.ui.test

import org.assertj.core.api.Assertions
import org.gradle.testkit.runner.GradleRunner

class GradleVersionCheckFunctionalTest {
    @get:Rule
    val temporaryFolder = TemporaryProjectFolder()

    @Test
    fun `build fails if Gradle version not supported`() {
        temporaryFolder.copyResource("simple.gradle", "build.gradle")

        val gradleVersion = "3.3"
        val result = GradleRunner.create()
                .withGradleVersion(gradleVersion)
                .withProjectDir(temporaryFolder.root)
                .withTestKitDir(temporaryFolder.newFolder())
                .withPluginClasspath()
                .withJaCoCo()
                .buildAndFail()

        Assertions.assertThat(result.output).contains("Gradle version $gradleVersion is not supported")
    }
}