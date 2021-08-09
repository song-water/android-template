package com.water.song.template.network

import io.ktor.http.*

sealed class NetworkResult<out T> {
    abstract val isOkay: Boolean
    class Okay<out T> (val value: T) : NetworkResult<T>() {
        override val isOkay: Boolean = true
        override fun toString(): String {
            return "NetworkResult.Okay{value=$value}"
        }
    }

    class Error(val error: HttpStatusCode) : NetworkResult<Nothing>() {
        override val isOkay: Boolean = false
        override fun toString(): String {
            return "NetworkResult.Error{error=$error}"
        }
    }
}