package com.varosyan.connecter

import com.varosyan.presenter.user_detail.UserDetailViewModel
import com.varosyan.presenter.user_list.UserListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun presenterModule() = module {
    viewModel { UserDetailViewModel(get()) }
    viewModel { UserListViewModel(get()) }
}