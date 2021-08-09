package com.water.song.template

import io.ktor.client.*

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual val httpClient: HttpClient
    get() = TODO("Not yet implemented")