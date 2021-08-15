package com.water.song.template

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val httpClient: HttpClient = HttpClient(Android) {
    engine {
        connectTimeout = 10_000
        socketTimeout = 10_000
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 10_000
    }
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.INFO
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
    Charsets {
        register(Charsets.UTF_8)
    }
}
actual val mainCoroutineDispatcher: CoroutineDispatcher
    get() = Dispatchers.Main