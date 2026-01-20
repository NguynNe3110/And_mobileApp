package com.uzuu.learn2_logic_saparation

class MainLogic {

    fun getGreeting(name: String) : String{
        if(name.isBlank()){
            return "chưa nhập tên!"
        }
        return "Xin chào $name"
    }
//    fun makeGreeting(name: String): String {
//        val trimmed = name.trim()
//        return if (trimmed.isEmpty()) {
//            "Bạn chưa nhập tên 😅"
//        } else {
//            "Xin chào $trimmed 👋"
//        }
//    }

}