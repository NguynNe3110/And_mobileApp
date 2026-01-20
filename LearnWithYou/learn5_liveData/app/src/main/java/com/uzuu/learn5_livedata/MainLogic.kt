package com.uzuu.learn5_livedata

class MainLogic {
    fun getGreeting(name: String): String {
        return if(name.isBlank()) {
            "ban khong nhap ten!"
        } else {
            "Hello $name"
        }
    }
}
