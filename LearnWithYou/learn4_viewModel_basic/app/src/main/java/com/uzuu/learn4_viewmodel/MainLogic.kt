package com.uzuu.learn4_viewmodel

class MainLogic {
    fun getGreeting(name :String) :String{
        return if(name.isBlank()){
            "Ban chua nhap ten!"
        } else {
            "xin chao ${name.trim()}"
        }
    }
}