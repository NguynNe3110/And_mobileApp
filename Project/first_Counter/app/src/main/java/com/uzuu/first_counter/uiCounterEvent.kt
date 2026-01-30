package com.uzuu.first_counter

sealed class uiCounterEvent {
    data class Toast(var message: String) : uiCounterEvent()
}