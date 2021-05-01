package com.water.song.template.tests

import android.app.Application
import android.util.Log

/**
 *  @author HelloWorld
 *  RobolectricApplication
 */
class RobolectricApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("AnyHelp", "Fuck")
    }
}