package com.uzuu.first_counter

data class uiCounterState (
    val count : Int = 0,
    val isLoading: Boolean = false,
    val state: String = "Thu cong",
    val isAuto: Boolean = false,
    val canUndo: Boolean = false
)
