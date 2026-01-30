package com.uzuu.learn11_repository

import android.os.Message

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
}