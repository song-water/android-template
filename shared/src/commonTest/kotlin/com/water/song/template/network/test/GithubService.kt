package com.water.song.template.network.test

import com.water.song.template.httpClient
import com.water.song.template.network.NetworkService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.jvm.JvmStatic

internal class GithubService private constructor() {
    private object Holder {
        val INSTANCE = GithubService()
    }

    companion object {
        @JvmStatic
        fun getInstance(): GithubService = Holder.INSTANCE
    }

    fun githubUser(client: HttpClient = httpClient, userName: String): NetworkService {
        return object : NetworkService() {
            override val requestBuilder: HttpRequestBuilder
                get() = HttpRequestBuilder().apply {
                    url("https://api.github.com/users/$userName")
                    method = HttpMethod.Get
                }

            override fun getClient(): HttpClient {
                return client
            }
        }
    }
}