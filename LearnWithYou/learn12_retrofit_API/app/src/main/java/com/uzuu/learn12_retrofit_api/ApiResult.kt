package com.uzuu.learn12_retrofit_api

//Để ViewModel dễ xử lý success/error, ta bọc kết quả.
sealed class ApiResult<out T> {
    data class Success<T>(val data:T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}