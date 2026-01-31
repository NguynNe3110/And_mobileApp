package com.uzuu.learn2_retrofit_api_basic.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET ("users/{id}")
    suspend fun getUser(@Path("id") id: Int) : UserDto
}