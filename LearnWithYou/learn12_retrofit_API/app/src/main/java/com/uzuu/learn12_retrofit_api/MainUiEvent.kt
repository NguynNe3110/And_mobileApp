package com.uzuu.learn12_retrofit_api

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
}