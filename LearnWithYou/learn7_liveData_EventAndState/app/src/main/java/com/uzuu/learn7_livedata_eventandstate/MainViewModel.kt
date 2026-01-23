package com.uzuu.learn7_livedata_eventandstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzuu.learn7_livedata_eventandstate.util.Event

class MainViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    // =========================== // ❌ SAI: dùng LiveData cho EVENT =====================

//    private val _toast = MutableLiveData<String>()
//    val toast: LiveData<String> = _toast
//    // =========================================================================================
//
//    fun greet(name: String) {
//        if (name.isBlank()) {
//            _toast.value = "Tên không được rỗng"
//        } else {
//            _message.value = "Hello $name"
//        }
//    }
// =========================== Cách 1 (CỔ ĐIỂN – dễ hiểu): EventWrapper ===========================

    private val _toastEvent = MutableLiveData<Event<String>>()
    val toastEvent: LiveData<Event<String>> = _toastEvent

    fun greet(name: String) {
        if (name.isBlank()) {
            _toastEvent.value = Event("Tên không được rỗng")
        } else {
            _message.value = "Hello $name"
        }
    }


// =========================================================================================

// =============== Cách 2 (HIỆN ĐẠI): StateFlow / SharedFlow =================================
//    StateFlow → State
//    SharedFlow → Event

// STATE
//private val _message = MutableLiveData<String>()
//    val message: LiveData<String> = _message
//
//    // EVENT
//    private val _toastEvent = MutableLiveData<Event<String>>()
//    val toastEvent: LiveData<Event<String>> = _toastEvent
//
//    fun greet(name: String) {
//        if (name.isBlank()) {
//            _toastEvent.value = Event("Tên không được rỗng")
//        } else {
//            _message.value = "Hello $name"
//        }
//    }
// =========================================================================================
}



