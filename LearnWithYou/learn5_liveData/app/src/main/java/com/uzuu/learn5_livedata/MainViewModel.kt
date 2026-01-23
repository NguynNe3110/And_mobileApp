package com.uzuu.learn5_livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
//logic
    private val logic = MainLogic()
//state
    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    // handle
    fun Greet(name: String){
        _message.value = logic.getGreeting(name)
    }
}

