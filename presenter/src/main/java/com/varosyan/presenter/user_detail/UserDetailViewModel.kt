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

    lateinit var  username: String

    private val _userDetail: MutableSharedFlow<UserDetail> = MutableSharedFlow()
    val userDetail: SharedFlow<UserDetail> = _userDetail.asSharedFlow()
    private val _loadingState: MutableStateFlow<DetailScreenState> =
        MutableStateFlow(DetailScreenState.Loading)
    val loadingState: StateFlow<DetailScreenState> = _loadingState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserDetailUseCase(username).safeHandle<UserDetail, Unit>(
                success = {
                    _userDetail.emit(it)
                    _loadingState.emit(value = DetailScreenState.Success)
                },
                handleError = { exception ->
                    _loadingState.emit(value = DetailScreenState.Error(exception.message.toString()))
                })
        }
    }
}