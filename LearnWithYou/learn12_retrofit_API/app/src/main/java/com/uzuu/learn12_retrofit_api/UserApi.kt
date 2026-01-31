package com.uzuu.learn12_retrofit_api

import retrofit2.http.GET
import retrofit2.http.Path

//Retrofit API interface
// đây là interface, k tự chạy được phải có class imple mới chạy
interface UserApi {
    // 1 hàm getUser

    // định nghĩa endpoint /users/{id} với id là placeholder
    @GET("users/{id}")
    // vì gọi trong mạng tốn tg -> dùng suspend chạy trong coroutine
    // @Path("id") id: Int -> tham soos id này sẽ được gắn vào {id}
    suspend fun getUser(@Path("id") id: Int) : UserDto
    //Retrofit + Gson sẽ:
    //parse JSON
    //trả object đã parse
    //KHÔNG phải string / raw response
}