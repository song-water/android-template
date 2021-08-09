package com.water.song.template.network

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface RequestService {
    fun call(
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    )

    fun call(
        timeoutMillis: Long = 60_000,
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    )

    fun delayCall(
        timeoutMillis: Long = 60_000,
        delayMillis: Long = 0,
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    )

    fun delayCall(
        delayMillis: Long,
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    )
}