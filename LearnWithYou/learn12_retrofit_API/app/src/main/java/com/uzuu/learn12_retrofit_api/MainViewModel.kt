package com.uzuu.learn12_retrofit_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    //ApiClient.userApi là instance duy nhất của Object UserApi
    // dù có gọi bao nhiêu lần ApiClient.userApi thì vẫn là 1 instance đó
    // -> đây gọi là singleton
    private val repo = UserRepository(ApiClient.userApi)

    //khai báo stateflow
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    //sharedflow
    private val _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadUser(id: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, text = "dang tai...") }

            when (val result = repo.getUser(id)) {
                is ApiResult.Success -> {
                    val user = result.data
                    _uiState.update {
                        it.copy(isLoading = false, text = "hello ${user.displayName} voi id = ${user.id}")
                    }
                    _uiEvent.tryEmit(MainUiEvent.Toast("Load successed!"))
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, text = result.message) }
                    _uiEvent.tryEmit(MainUiEvent.Toast("Load failed!"))
                }
            }
        }
    }
}