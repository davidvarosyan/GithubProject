package com.varosyan.presenter.user_list

import androidx.paging.PagingData
import com.varosyan.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Represents the UI state for the UserList screen
 */
data class UserListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val users: Flow<PagingData<User>>? = null,
    val isLoadMoreLoading: Boolean = false,
    val loadMoreError: String? = null
)

/**
 * Represents user intents/actions
 */
sealed class UserListIntent {
    object LoadUsers : UserListIntent()
    object Retry : UserListIntent()
    object RetryLoadMore : UserListIntent()
    data class UserClicked(val user: User) : UserListIntent()
    object ClearError : UserListIntent()
}

/**
 * Represents side effects that should be handled by the UI
 */
sealed class UserListEffect {
    data class NavigateToUserDetail(val userName: String) : UserListEffect()
    data class ShowError(val message: String) : UserListEffect()
    data class ShowLoadMoreError(val message: String) : UserListEffect()
}

