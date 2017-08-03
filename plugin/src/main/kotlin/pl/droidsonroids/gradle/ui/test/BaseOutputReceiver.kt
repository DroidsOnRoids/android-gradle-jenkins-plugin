package pl.droidsonroids.gradle.ui.test

import com.android.ddmlib.MultiLineReceiver

abstract class BaseOutputReceiver : MultiLineReceiver() {
    override fun isCancelled() = false
}
