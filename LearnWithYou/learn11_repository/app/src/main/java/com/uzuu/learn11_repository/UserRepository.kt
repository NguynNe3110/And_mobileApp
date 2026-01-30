package com.uzuu.learn11_repository

import kotlinx.coroutines.delay

class UserRepository(
    private val dataSource: FakeUserDataSource
) {
    suspend fun getUser() : User {
        // Sau này: ở đây sẽ quyết định Remote/Local/Cache
        return dataSource.fetchUser()
    }
}