package com.water.song.template.network

import kotlin.coroutines.CoroutineContext

interface RequestCallback {
    fun onSuccess(
        dispatcher: CoroutineContext? = null,
        callCallback: (NetworkResult.Okay<String>) -> Unit
    ): RequestCallback

    fun onFailure(
        dispatcher: CoroutineContext? = null,
        callCallback: (NetworkResult.Error) -> Unit
    ): RequestCallback

    fun onComplete(
        dispatcher: CoroutineContext? = null,
        callCallback: (Boolean) -> Unit
    ): RequestService
}