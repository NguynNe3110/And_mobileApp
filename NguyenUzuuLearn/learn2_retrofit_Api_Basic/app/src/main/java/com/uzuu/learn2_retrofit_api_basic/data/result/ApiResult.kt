package com.uzuu.learn2_retrofit_api_basic.data.result

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String): ApiResult<Nothing>()
}