package com.uzuu.learn11_repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo = UserRepository(FakeUserDataSource())
    private val _uiState = MutableStateFlow(MainUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    fun onLoadUser(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, tvResult = "Dang tai...")}

            try {
                val user = repo.getUser()
                _uiState.update { it.copy(isLoading = false, tvResult = "name : ${user.name}, id: ${user.id}") }
                _uiEvent.tryEmit(MainUiEvent.Toast("Load thanh cong!"))
            }catch (e: Exception){
                _uiEvent.tryEmit(MainUiEvent.Toast("Loi: ${e.message}"))
            }
        }
    }
}