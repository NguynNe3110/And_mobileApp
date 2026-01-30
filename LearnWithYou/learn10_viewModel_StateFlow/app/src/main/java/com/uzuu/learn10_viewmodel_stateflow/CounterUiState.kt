package com.uzuu.learn10_viewmodel_stateflow

data class CounterUiState (
    val intCount: Int = 0,
    val isLoading: Boolean = false,
    val isAuto: Boolean = false,
    val tvState: String = "Thủ công",
    val canUndo: Boolean = false
)
