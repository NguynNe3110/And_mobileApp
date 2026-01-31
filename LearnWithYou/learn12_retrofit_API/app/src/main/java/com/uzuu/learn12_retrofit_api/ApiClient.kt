package com.uzuu.learn12_retrofit_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Retrofit client (tối giản)
//dùng object để đảm bảo chỉ 1 instance duy nhất
object ApiClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            //đọc interface UserApi -> từng fun trong đó
            // và lưu thông tin lại (gồm func, endpoint, type return)
            // -> retrofit tạo ra 1 object giả implement UserApi
            .create(UserApi::class.java)
    }
}