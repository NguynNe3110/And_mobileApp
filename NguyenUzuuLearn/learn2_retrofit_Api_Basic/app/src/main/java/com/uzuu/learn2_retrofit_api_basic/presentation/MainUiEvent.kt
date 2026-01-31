package com.uzuu.learn2_retrofit_api_basic.presentation

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
}