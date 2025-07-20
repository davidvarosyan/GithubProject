package com.varosyan.presenter.user_list

import androidx.lifecycle.ViewModel
import com.varosyan.domain.usecase.GetUsersUseCase

class UserListViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
}