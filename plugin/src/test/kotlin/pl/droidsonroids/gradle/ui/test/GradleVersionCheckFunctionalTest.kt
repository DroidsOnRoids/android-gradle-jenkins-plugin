package pl.droidsonroids.gradle.ui.test

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.Test

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

        assertThat(result.output).contains("Gradle version $gradleVersion is not supported")
    }
}