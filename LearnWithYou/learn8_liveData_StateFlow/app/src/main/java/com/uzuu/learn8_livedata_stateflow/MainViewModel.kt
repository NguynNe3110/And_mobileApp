package com.uzuu.learn8_livedata_stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val isLoading: Boolean = false,
    val resultText: String = "Kết quả sẽ hiện ở đây",
    val isButtonEnabled: Boolean = true
)

sealed class UiEvent {
    data class Toast(val message: String) : UiEvent()
}

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    // Event 1 lần (toast, navigate...) -> SharedFlow
    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    fun onSearchClick(query: String) {
        if (query.isBlank()) {
            viewModelScope.launch { _event.emit(UiEvent.Toast("Bạn chưa nhập gì")) }
            return
        }

        viewModelScope.launch {
            // 1) set loading
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                isButtonEnabled = false,
                resultText = "Đang tìm: \"$query\" ..."
            )

            // giả lập call API
            delay(1000)

            // 2) cập nhật kết quả
            val fakeResult = "Kết quả cho \"$query\": ${query.reversed()}"
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isButtonEnabled = true,
                resultText = fakeResult
            )

            // 3) bắn event 1 lần
            _event.emit(UiEvent.Toast("Tìm xong rồi"))
        }
    }
}
