package com.uzuu.learn12_retrofit_api

//Để ViewModel dễ xử lý success/error, ta bọc kết quả.
// class ApiResult để biểu diễn trạng thái trả về là success hay error
//muoosn truy cập để xem trạng thái thì dùng when để kiểm tra
sealed class ApiResult<out T> {
    // kiểu dữ liệu T chỉ được dùng để return
    data class Success<T>(val data:T) : ApiResult<T>()
    // với lỗi thì không có data chỉ có message th
    data class Error(val message: String) : ApiResult<Nothing>()
}