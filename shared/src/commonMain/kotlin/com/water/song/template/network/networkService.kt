package com.water.song.template.network

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class NetworkService : RequestService, RequestCallback {
    protected abstract val requestBuilder: HttpRequestBuilder
    protected abstract fun getClient(): HttpClient

    private var success: Callback<NetworkResult.Okay<String>>? = null
    private var fail: Callback<NetworkResult.Error>? = null
    private var complete: Callback<Boolean>? = null

    private fun isCallbackSet(): Boolean {
        if (success != null || fail != null || complete != null) {
            return true
        }
        return false
    }

    override fun call(workScope: CoroutineScope?, coroutineContext: CoroutineContext?): Job {
        return delayCall(0, 0, workScope, coroutineContext)
    }

    override fun call(
        timeoutMillis: Long,
        workScope: CoroutineScope?,
        coroutineContext: CoroutineContext?
    ) : Job{
        return delayCall(timeoutMillis, 0, workScope, coroutineContext)
    }

    override fun delayCall(
        timeoutMillis: Long,
        delayMillis: Long,
        workScope: CoroutineScope?,
        coroutineContext: CoroutineContext?
    ): Job {
        val callScope = workScope ?: GlobalScope
        return getClient().let {
            val callContext = coroutineContext ?: it.coroutineContext
            callScope.launch(callContext) {
                awaitAndDispatchResult(it, timeoutMillis, delayMillis)
            }
        }
    }

    override fun delayCall(
        delayMillis: Long,
        workScope: CoroutineScope?,
        coroutineContext: CoroutineContext?
    ) : Job{
        return delayCall(0, delayMillis, workScope, coroutineContext)
    }

    private suspend fun awaitAndDispatchResult(
        client: HttpClient,
        timeoutMillis: Long,
        delayMillis: Long
    ) {
        if (delayMillis > 0) {
            delay(delayMillis)
        }
        val result = client.awaitTimeout(requestBuilder, timeoutMillis)
        if (isCallbackSet()) {
            dispatchResult(result)
        }
    }

    private suspend fun dispatchResult(result: NetworkResult<String>) {
        val httpResult = when(result) {
            is NetworkResult.Okay -> {
                success?.let {
                    withContext(it.dispatcher) {
                        it.callback.invoke(result)
                    }
                }
                true
            }
            is NetworkResult.Error -> {
                fail?.let {
                    withContext(it.dispatcher) {
                        it.callback.invoke(result)
                    }
                }
                false
            }
        }
        complete?.let {
            withContext(it.dispatcher) {
                it.callback.invoke(httpResult)
            }
        }
        clear()
    }

    override fun onSuccess(
        dispatcher: CoroutineContext?,
        callCallback: (NetworkResult.Okay<String>) -> Unit
    ): RequestCallback {
        if (this.success == null) {
            this.success = Callback<NetworkResult.Okay<String>>().also {
                if (dispatcher != null) {
                    it.dispatcher = dispatcher
                }
                it.callback = callCallback
            }
        } else {
            throw DuplicateAssignmentException("success is not null")
        }
        return this
    }

    override fun onFailure(
        dispatcher: CoroutineContext?,
        callCallback: (NetworkResult.Error) -> Unit
    ): RequestCallback {
        if (this.fail == null) {
            this.fail = Callback<NetworkResult.Error>().also {
                if (dispatcher != null) {
                    it.dispatcher = dispatcher
                }
                it.callback = callCallback
            }
        } else {
            throw DuplicateAssignmentException("fail is not null")
        }
        return this
    }

    override fun onComplete(
        dispatcher: CoroutineContext?,
        callCallback: (Boolean) -> Unit
    ): RequestService {
        if (this.complete == null) {
            this.complete = Callback<Boolean>().also {
                if (dispatcher != null) {
                    it.dispatcher = dispatcher
                }
                it.callback = callCallback
            }
        } else {
            throw DuplicateAssignmentException("completion is not null")
        }
        return this
    }

    private fun clear() {
        success = null
        fail = null
        complete = null
    }
}

internal class Callback<V: Any> {
    internal var dispatcher: CoroutineContext = Dispatchers.Main
    internal var callback: (V) -> Unit = {}
}