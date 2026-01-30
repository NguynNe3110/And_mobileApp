package com.uzuu.learn11_repository

import kotlinx.coroutines.delay
// gia lap nhan du lieu
class FakeUserDataSource {
    suspend fun fetchUser() :User {
        delay(800)
        return User(id = 1, name = "nguyenuzuu")
    }
}