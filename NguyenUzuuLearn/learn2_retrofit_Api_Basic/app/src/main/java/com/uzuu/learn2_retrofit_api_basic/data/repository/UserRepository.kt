package com.uzuu.learn2_retrofit_api_basic.data.repository

import com.uzuu.learn2_retrofit_api_basic.data.mapper.toDomain
import com.uzuu.learn2_retrofit_api_basic.data.remote.UserApi
import com.uzuu.learn2_retrofit_api_basic.data.result.ApiResult
import com.uzuu.learn2_retrofit_api_basic.domain.User
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class UserRepository(
    private val api: UserApi
) {
    suspend fun getUser(id: Int): ApiResult<User>{
        return try {
            val dto = api.getUser(id)
//            val a = UserMapper()
//            val user = a.toDomain(dto)
            val user = dto.toDomain()
            ApiResult.Success(user)
        } catch (e: IOException) {
            ApiResult.Error("mat mang, loi ket noi!")
        } catch (e: HttpException) {
            ApiResult.Error("HTTP ${e.code()} ${e.message()}")
        } catch (e: Exception) {
            ApiResult.Error("Loi khong xac dinh! ${e.message}")
        }
    }
}