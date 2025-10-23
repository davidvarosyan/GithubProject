package com.varosyan.presenter.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UserDetailEffect>()
    val effects: SharedFlow<UserDetailEffect> = _effects.asSharedFlow()

    private var username: String = ""

    fun initialize(username: String) {
        this.username = username
        _uiState.value = _uiState.value.copy(isLoading = true)
        handleIntent(UserDetailIntent.LoadUserDetail)
    }

    fun handleIntent(intent: UserDetailIntent) {
        when (intent) {
            is UserDetailIntent.LoadUserDetail -> loadUserDetail()
            is UserDetailIntent.Retry -> retry()
            is UserDetailIntent.ClearError -> clearError()
        }
    }

    private fun loadUserDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserDetailUseCase(username).safeHandle<UserDetail, Unit>(
                success = { userDetail ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userDetail = userDetail,
                        error = null
                    )
                },
                handleError = { exception ->
                    val errorMessage = exception.message ?: "An error occurred"
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                    _effects.emit(UserDetailEffect.ShowError(errorMessage))
                }
            )
        }
    }

    private fun retry() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )
        loadUserDetail()
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}