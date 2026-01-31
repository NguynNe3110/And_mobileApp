package com.uzuu.learn12_retrofit_api

import retrofit2.HttpException
import java.io.IOException

class UserRepository(
    private val api: UserApi
) {
    suspend fun getUser(id: Int): ApiResult<User> {
        return try {
            // k cần imple nhưng vẫn gọi được getUser thuộc ìnterface UserApi
            //Retrofit tạo ra 1 object implement interface này LÚC APP ĐANG CHẠY
            //(chứ không phải lúc compile)
            // nó được gọi là runtime-generated object trong retrofit
            val dto = api.getUser(id)
            // khi gọi đến api thì retrofit làm hết (tạo request, gửi, nhận, parse(Gson), return Dto)
            //thực tế (tạo URL, gắn path {id} = 1, gửi HTTP bằng Okhttp, nhận JSON, parse bằng Gson, trả về UserDto)
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