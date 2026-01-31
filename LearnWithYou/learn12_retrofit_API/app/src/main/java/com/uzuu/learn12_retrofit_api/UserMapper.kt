package com.uzuu.learn12_retrofit_api

// vieest theo kiểu extension function
//(nhận vào kiểu dữ liệu UserDto và trả về User với tên hàm là toDomain())
fun UserDto.toDomain(): User {
    return User(
        id = id,
        displayName = name
    )
}
