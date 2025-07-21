package com.varosyan.presenter.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.usecase.GetUserDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val username: String,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    private val _userDetail: MutableSharedFlow<UserDetail> = MutableSharedFlow()
    val userDetail: SharedFlow<UserDetail> = _userDetail.asSharedFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _userDetail.emit(getUserDetailUseCase(username))
        }
    }
}