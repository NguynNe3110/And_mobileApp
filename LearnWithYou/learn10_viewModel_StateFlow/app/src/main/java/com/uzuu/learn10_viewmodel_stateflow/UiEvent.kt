package com.uzuu.learn10_viewmodel_stateflow

sealed class UiEvent {
    data class Toast(val message: String) : UiEvent()
}