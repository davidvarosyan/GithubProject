package com.varosyan.presenter.user_detail

import androidx.lifecycle.ViewModel
import com.varosyan.domain.usecase.GetUserDetailUseCase

class UserDetailViewModel(private val getUserDetailUseCase: GetUserDetailUseCase) : ViewModel() {
}