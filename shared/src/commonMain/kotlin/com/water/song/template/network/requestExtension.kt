package com.water.song.template.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*
import kotlinx.coroutines.withTimeout

internal suspend fun HttpClient.awaitTimeout(
    requestConfig: HttpRequestBuilder,
    timeoutMillis: Long = 0
): NetworkResult<String> {
    return this.let { client ->
        kotlin.runCatching {
            if (timeoutMillis > 0) {
                withTimeout(timeoutMillis) {
                    await(client, requestConfig)
                }
            } else {
                await(client, requestConfig)
            }
        }.getOrElse {
            println("HelloWorld awaitTimeout " + it.message)
            NetworkResult.Error(HttpStatusCode(666, it.message ?: ""))
        }
    }
}

private suspend inline fun await(
    httpClient: HttpClient,
    requestConfig: HttpRequestBuilder
): NetworkResult<String> {
    return httpClient.request<HttpResponse>(requestConfig).let { response ->
        if (response.status.isSuccess()) {
            val result = response.readText(Charsets.UTF_8)
            NetworkResult.Okay(result)
        } else {
            println("HelloWorld await ")
            NetworkResult.Error(response.status)
        }
    }
}