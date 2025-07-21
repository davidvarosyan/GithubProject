package com.varosyan.domain.common


suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (exception: Exception) {
        Result.Error(exception)
    }
}



