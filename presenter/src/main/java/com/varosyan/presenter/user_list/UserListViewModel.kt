package com.varosyan.presenter.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.varosyan.domain.model.User
import com.varosyan.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListUiState())
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UserListEffect>()
    val effects: SharedFlow<UserListEffect> = _effects.asSharedFlow()

    private val usersFlow: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 50, initialLoadSize = 50),
        pagingSourceFactory = { UserPagingSource(getUsersUseCase) }
    ).flow
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    init {
        handleIntent(UserListIntent.LoadUsers)
    }

    fun handleIntent(intent: UserListIntent) {
        when (intent) {
            is UserListIntent.LoadUsers -> loadUsers()
            is UserListIntent.Retry -> retry()
            is UserListIntent.RetryLoadMore -> retryLoadMore()
            is UserListIntent.UserClicked -> navigateToUserDetail(intent.user)
            is UserListIntent.ClearError -> clearError()
        }
    }

    private fun loadUsers() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null,
            users = usersFlow
        )
    }

    private fun retry() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )
        loadUsers()
    }

    private fun retryLoadMore() {
        _uiState.value = _uiState.value.copy(
            isLoadMoreLoading = true,
            loadMoreError = null
        )
    }

    private fun navigateToUserDetail(user: User) {
        viewModelScope.launch {
            _effects.emit(UserListEffect.NavigateToUserDetail(user.userName))
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun handleLoadState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            is LoadState.NotLoading -> {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
            is LoadState.Error -> {
                val errorMessage = when (loadState.error) {
                    is IOException -> "No internet connection"
                    else -> "An error occurred: ${loadState.error.message}"
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = errorMessage
                )
                viewModelScope.launch {
                    _effects.emit(UserListEffect.ShowError(errorMessage))
                }
            }
        }
    }

    fun handleAppendLoadState(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                _uiState.value = _uiState.value.copy(isLoadMoreLoading = true)
            }
            is LoadState.NotLoading -> {
                _uiState.value = _uiState.value.copy(isLoadMoreLoading = false)
            }
            is LoadState.Error -> {
                val errorMessage = "Error loading more: ${loadState.error.message}"
                _uiState.value = _uiState.value.copy(
                    isLoadMoreLoading = false,
                    loadMoreError = errorMessage
                )
                viewModelScope.launch {
                    _effects.emit(UserListEffect.ShowLoadMoreError(errorMessage))
                }
            }
        }
    }
}