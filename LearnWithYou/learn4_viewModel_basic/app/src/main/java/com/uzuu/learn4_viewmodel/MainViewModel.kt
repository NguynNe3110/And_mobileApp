package com.uzuu.learn4_viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    //logic usecase (xử lý nghiệp vụ)
    private val logic = MainLogic()

    //state (dữ liệu)
    var message: String = ""
        private set

    //event handle (hàm nhận sự kiện)
    fun greet(name: String){
        message = logic.getGreeting(name)
    }
}
