package com.uzuu.learn2_retrofit_api_basic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn2_retrofit_api_basic.data.remote.ApiClient
import com.uzuu.learn2_retrofit_api_basic.data.repository.UserRepository
import com.uzuu.learn2_retrofit_api_basic.data.result.ApiResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val repo = UserRepository(ApiClient.userApi)

    private var _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private var _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    fun onLoadingUser(id: Int){
        viewModelScope.launch {
            // truoc khi thao tac
            _uiState.update {
                it.copy(
                    isLoading = true,
                    result = "dang tai..."
                )
            }
            when (val result = repo.getUser(id)) {
                is ApiResult.Success -> {
                    val user = result.data
                    _uiState.update {
                        it.copy(isLoading = false, result = "hello ${user.nameDisplay} voi id = ${user.id}")
                    }
                    _uiEvent.tryEmit(MainUiEvent.Toast("Load successed!"))
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, result = result.message) }
                    _uiEvent.tryEmit(MainUiEvent.Toast("Load failed!"))
                }
            }
        }
    }
}