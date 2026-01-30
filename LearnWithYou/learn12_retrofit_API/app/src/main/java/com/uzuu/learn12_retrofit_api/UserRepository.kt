package com.uzuu.learn12_retrofit_api

import retrofit2.HttpException
import java.io.IOException

class UserRepository(
    private val api: UserApi
) {
    suspend fun getUser(id: Int): ApiResult<User> {
        return try {
            val dto = api.getUser(id)
            val user = dto.toDomain()
            ApiResult.Success(user)
        } catch (e: IOException){
            ApiResult.Error("mat mang/ loi ket noi")
        } catch (e: HttpException) {
            ApiResult.Error("HTTP ${e.code()} (${e.message()})")
        } catch (e: Exception) {
            ApiResult.Error("Lỗi không xác định: ${e.message ?: "unknown"}")
        }
    }
}