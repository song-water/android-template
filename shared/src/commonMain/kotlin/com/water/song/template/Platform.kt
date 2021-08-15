package com.water.song.template

import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher

expect class Platform() {
    val platform: String
}

expect val httpClient: HttpClient

expect val mainCoroutineDispatcher: CoroutineDispatcher