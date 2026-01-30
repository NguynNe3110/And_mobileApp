package com.uzuu.learn10_viewmodel_stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    private var autoJob: Job? = null
    private var lastCount: Int? = null

    private val MIN = 0
    private val MAX = 10

    fun onPlus(fromAuto: Boolean = false) {
        val state = _uiState.value
        if (state.isLoading) {
            if (!fromAuto) _uiEvent.tryEmit(UiEvent.Toast("Đang loading, chờ chút!"))
            return
        }

        if (state.intCount >= MAX) {
            if (!fromAuto) _uiEvent.tryEmit(UiEvent.Toast("Max reached"))
            // nếu auto mà chạm max → tự tắt
            if (fromAuto) stopAutoWithToast("Auto stopped: max reached")
            return
        }

        lastCount = state.intCount
        _uiState.update { old ->
            old.copy(
                intCount = old.intCount + 1,
                canUndo = true
            )
        }
    }

    fun onMinus() {
        val state = _uiState.value
        if (state.isLoading) {
            _uiEvent.tryEmit(UiEvent.Toast("Đang loading, chờ chút!"))
            return
        }
        if (state.intCount <= MIN) {
            _uiEvent.tryEmit(UiEvent.Toast("Min reached"))
            return
        }

        lastCount = state.intCount
        _uiState.update { old ->
            old.copy(
                intCount = old.intCount - 1,
                canUndo = true
            )
        }
    }

    fun onReset() {
        // reset cũng nên tắt auto
        stopAutoSilently()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(500)

            lastCount = null
            _uiState.update {
                it.copy(
                    intCount = 0,
                    isLoading = false,
                    canUndo = false,
                    tvState = "Thủ công",
                    isAuto = false
                )
            }
            _uiEvent.emit(UiEvent.Toast("Reset done!"))
        }
    }

    fun onUndo() {
        val prev = lastCount
        if (prev == null) {
            _uiEvent.tryEmit(UiEvent.Toast("Không có gì để undo!"))
            return
        }

        _uiState.update {
            it.copy(
                intCount = prev,
                canUndo = false
            )
        }
        lastCount = null
        _uiEvent.tryEmit(UiEvent.Toast("Undo ok"))
    }

    fun onAutoToggle(enabled: Boolean) {
        if (enabled) startAuto() else stopAutoSilently()
    }

    private fun startAuto() {
        // nếu đang max thì khỏi bật
        if (_uiState.value.intCount >= MAX) {
            _uiEvent.tryEmit(UiEvent.Toast("Đang ở max, không bật auto được"))
            _uiState.update { it.copy(isAuto = false, tvState = "Thủ công") }
            return
        }

        // set state auto on
        _uiState.update { it.copy(isAuto = true, tvState = "Auto") }

        autoJob?.cancel()
        autoJob = viewModelScope.launch {
            while (isActive && _uiState.value.isAuto) {
                delay(1000)
                onPlus(fromAuto = true)
            }
        }
    }

    private fun stopAutoSilently() {
        autoJob?.cancel()
        autoJob = null
        _uiState.update { it.copy(isAuto = false, tvState = "Thủ công") }
    }

    private fun stopAutoWithToast(msg: String) {
        autoJob?.cancel()
        autoJob = null
        _uiState.update { it.copy(isAuto = false, tvState = "Thủ công") }
        _uiEvent.tryEmit(UiEvent.Toast(msg))
    }
}
