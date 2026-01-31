package com.uzuu.learn2_retrofit_api_basic.data.mapper

import com.uzuu.learn2_retrofit_api_basic.data.remote.UserDto
import com.uzuu.learn2_retrofit_api_basic.domain.User

//class UserMapper(){
//    fun toDomain(dto : UserDto): User {
//        return User(
//            id = dto.id,
//            nameDisplay = dto.name
//        )
//    }
//}

fun UserDto.toDomain(): User {
    return User(
        id = id,
        nameDisplay = name
    )
}