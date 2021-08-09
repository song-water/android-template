package com.water.song.template.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.water.song.template.android.R
import com.water.song.template.network.test.BaiduApiService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        BaiduApiService.getInstance().baiduService()
            .onSuccess {
                Log.e("HelloWorld", "onSuccess " + it.value)
            }
            .onFailure {
                Log.e("HelloWorld", "onFailure " + it.error.description)
            }
            .onComplete {
                Log.e("HelloWorld", "onComplete $it")
            }
            .call(timeoutMillis = 10000)
    }
}