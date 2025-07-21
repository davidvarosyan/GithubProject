package com.varosyan.presenter.user_detail


sealed class DetailScreenState {
    data object Loading : DetailScreenState()
    data class Error(val message: String) : DetailScreenState()
    data object Success : DetailScreenState()
}