package pl.droidsonroids.gradle.ui.test

import com.android.utils.ILogger

class LoggingOutputReceiver(val logger: ILogger) : BaseOutputReceiver() {
    override fun processNewLines(lines: Array<String>) = lines.forEach { logger.info(it) }
}