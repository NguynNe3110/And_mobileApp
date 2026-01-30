package com.uzuu.first_counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(uiCounterState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<uiCounterEvent>(extraBufferCapacity = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    private val MIN = 0
    private val MAX = 10

    private var autoJob: Job? = null
    private var lastCount:Int? = null

    fun onPlus(Auto: Boolean = false){
        val state = _uiState.value
        if(state.isLoading){
            _uiEvent.tryEmit(uiCounterEvent.Toast("đang load, chờ chút!"))
            return
        }
        if(state.count >= MAX){
            if(Auto){
                _uiEvent.tryEmit(uiCounterEvent.Toast("đạt max : auto -> tắt"))
                return
            } else {
                _uiEvent.tryEmit(uiCounterEvent.Toast("đạt giá trị max rồi!"))
                return
            }
        } else {
            lastCount = state.count
            _uiState.update { old->
                old.copy(
                    count = old.count + 1,
                    canUndo = true
                )
            }
        }

    }

    fun onMinus(){
        val state = _uiState.value
        if(state.isLoading){
            _uiEvent.tryEmit(uiCounterEvent.Toast("đang load, chờ tý!"))
            return
        }
        if(state.count <= 0){
            _uiEvent.tryEmit(uiCounterEvent.Toast("đạt min rồi!"))
            return
        }

        lastCount = state.count
        _uiState.update {
            it.copy(
                count = it.count - 1,
                canUndo = true
            )
        }

    }

    fun onReset(){

        stopAuto()

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                state = "dang load...",
            )
            delay(1000)
            lastCount = 0

            _uiState.update { old->
                old.copy(
                    count = 0,
                    state = "thu cong",
                    isLoading = false,
                    canUndo = false,
                    isAuto = false
                )
            }
            _uiEvent.tryEmit(uiCounterEvent.Toast("da load xong!"))
        }
    }

    fun onUndo(){
        val pre = lastCount
        if(pre == null){
            _uiEvent.tryEmit(uiCounterEvent.Toast("no thing!"))
            return
        }
        _uiState.value = _uiState.value.copy(
            count = pre,
            canUndo = false
        )
        _uiEvent.tryEmit(uiCounterEvent.Toast("da undo!"))
    }

    fun onAuto(enable: Boolean){
        if(enable) startAuto() else stopAuto()
    }

    fun startAuto(){
        if (_uiState.value.count >= MAX) {
            _uiEvent.tryEmit(uiCounterEvent.Toast("Đang ở max, không bật auto được"))
            _uiState.update { it.copy(isAuto = false, state = "Thủ công") }
            return
        }

        _uiState.update { it.copy(isAuto = true, state = "Auto")}

        autoJob?.cancel()
        autoJob = viewModelScope.launch {
            while (isActive && _uiState.value.isAuto){
                delay(1000)
                onPlus(Auto = true)
            }
        }
    }

    fun stopAuto(){
        autoJob?.cancel()
        autoJob = null
        _uiState.update { it.copy(isAuto = false, state = "Thủ công") }
    }
}