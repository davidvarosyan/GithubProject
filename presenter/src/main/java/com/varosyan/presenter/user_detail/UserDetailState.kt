package com.varosyan.presenter.user_detail

import com.varosyan.domain.model.UserDetail

/**
 * Represents the UI state for the UserDetail screen
 */
data class UserDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userDetail: UserDetail? = null
)

/**
 * Represents user intents/actions for UserDetail
 */
sealed class UserDetailIntent {
    object LoadUserDetail : UserDetailIntent()
    object Retry : UserDetailIntent()
    object ClearError : UserDetailIntent()
}

/**
 * Represents side effects that should be handled by the UI
 */
sealed class UserDetailEffect {
    data class ShowError(val message: String) : UserDetailEffect()
    data object  NavigateBack : UserDetailEffect()
}

