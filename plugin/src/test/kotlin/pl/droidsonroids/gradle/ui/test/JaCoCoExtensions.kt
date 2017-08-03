package pl.droidsonroids.gradle.ui.test

import org.gradle.testkit.runner.GradleRunner
import java.io.File

fun GradleRunner.withJaCoCo(): GradleRunner {
    javaClass.classLoader.getResourceAsStream("testkit-gradle.properties").toFile(File(projectDir, "gradle.properties"))
    return this
}