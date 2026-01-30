package com.uzuu.learn12_retrofit_api

import retrofit2.http.GET
import retrofit2.http.Path

//Retrofit API interface
interface UserApi {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int) : UserDto
}