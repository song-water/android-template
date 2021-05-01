package com.water.song.template.tests

import android.app.Application
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * @author HelloWorld
 * DemoRobolectricTestRunner
 */
class WaterRobolectricTestRunner(testClass: Class<*>?) : RobolectricTestRunner(testClass) {

    companion object {
        private const val DEFAULT_SDK = 28
    }

    override fun buildGlobalConfig(): Config {
        return Config.Builder()
            .setApplication(Application::class.java)
            .setSdk(DEFAULT_SDK)
            .build()
    }
}