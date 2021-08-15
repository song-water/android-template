package com.water.song.template

import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual val httpClient: HttpClient
    get() = TODO("Not yet implemented")
actual val mainCoroutineDispatcher: CoroutineDispatcher
    get() = Dispatchers.Main