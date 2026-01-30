package com.uzuu.learn12_retrofit_api

fun UserDto.toDomain(): User {
    return User(
        id = id,
        displayName = name
    )
}
