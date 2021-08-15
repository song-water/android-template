package com.water.song.template.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface RequestService {
    fun call(
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    ): Job

    fun call(
        timeoutMillis: Long = 60_000,
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    ): Job

    fun delayCall(
        timeoutMillis: Long = 60_000,
        delayMillis: Long = 0,
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    ): Job

    fun delayCall(
        delayMillis: Long,
        workScope: CoroutineScope? = null,
        coroutineContext: CoroutineContext? = null
    ): Job
}