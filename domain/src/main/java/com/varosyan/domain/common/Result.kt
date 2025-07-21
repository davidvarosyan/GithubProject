package com.varosyan.domain.common

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()

    inline fun <R> fold(
        onSuccess: (T) -> R, onError: (Throwable) -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(exception)
    }

    suspend fun <T, K> safeHandle(success: suspend (T) -> K, handleError: suspend (Throwable) -> K): K {
        return when (this) {
            is Error -> handleError(exception)
            is Success<*> -> success(this.data as T)
        }
    }
}

