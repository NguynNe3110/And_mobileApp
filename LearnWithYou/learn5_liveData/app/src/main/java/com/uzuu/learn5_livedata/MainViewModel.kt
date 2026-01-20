package com.uzuu.learn5_livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val logic = MainLogic()

    // STATE (bên ngoài chỉ đọc)
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun greet(name: String) {
        _message.value = logic.getGreeting(name)
    }
}
