package com.example.network

import retrofit2.Response
import timber.log.Timber
import java.io.IOException


sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error<out T>(val exception: Exception) : ApiResult<T>()
}

suspend fun <T> wrapInApiResult(call: suspend () -> Response<T>): ApiResult<T> =
    try {
        call().toApiResult()
    } catch (e: Exception) {
        Timber.e(e, "Error while calling api.")
        ApiResult.Error(IOException(e))
    }

private fun <T> Response<T>.toApiResult(): ApiResult<T> {
    val body = body()
    return if (isSuccessful && body != null) {
        ApiResult.Success(body)
    } else {
        ApiResult.Error(IOException())
    }
}