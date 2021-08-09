package com.water.song.template.network.test

import com.water.song.template.httpClient
import com.water.song.template.network.NetworkService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.jvm.JvmStatic

class BaiduApiService private constructor() {
    private object Holder {
        val INSTANCE = BaiduApiService()
    }

    companion object {
        @JvmStatic
        fun getInstance(): BaiduApiService = Holder.INSTANCE
    }

    fun baiduService(): NetworkService {
        return object : NetworkService() {
            override val requestBuilder: HttpRequestBuilder
                get() = HttpRequestBuilder().apply {
                    url("https://www.baidu.com")
                    method = HttpMethod.Get
                }

            override fun getClient(): HttpClient {
                return httpClient
            }
        }
    }
}