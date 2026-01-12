package com.uzuu.learn1_mvvm.ui.screen.main

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // STATE (dữ liệu)
    private val _text = mutableStateOf("Hello Android")
    val text: State<String> = _text

    // LOGIC
    fun changeText() {
        _text.value = "Bạn vừa bấm nút 👋"
    }
}
