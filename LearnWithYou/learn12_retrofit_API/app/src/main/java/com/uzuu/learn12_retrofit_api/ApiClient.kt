package com.uzuu.learn12_retrofit_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Retrofit client (tối giản)
object ApiClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}