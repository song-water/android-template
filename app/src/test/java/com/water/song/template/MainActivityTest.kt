package com.water.song.template

import android.net.Uri
import android.os.Bundle
import com.water.song.template.tests.WaterRobolectricTestRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * @author HelloWorld
 * MainActivityTest
 */
@RunWith(WaterRobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainActivityTest {

    @Test
    fun test_onCreate() {
        val bundle = Bundle()
        bundle.putString("Any", "HelloWorlLLLD")
        val uri = Uri.parse("http://www.baidu.com?word=himan")
        println("-->" + bundle.getString("Any"))
        println("-->" + uri.authority)
        println("-->" + uri.path)
        println("-->" + uri.port)
    }
}