package com.varosyan.presenter.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.varosyan.domain.model.User
import com.varosyan.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.Flow

class UserListViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {


    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 50, initialLoadSize = 50),
        pagingSourceFactory = { UserPagingSource(getUsersUseCase) }
    ).flow
        .cachedIn(viewModelScope)


}