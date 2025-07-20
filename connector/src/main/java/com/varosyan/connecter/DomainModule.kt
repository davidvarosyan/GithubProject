package com.varosyan.connecter

import com.varosyan.domain.usecase.GetUserDetailUseCase
import com.varosyan.domain.usecase.GetUsersUseCase
import com.varosyan.domain.usecaseimpl.GetUserDetailUseCaseImpl
import com.varosyan.domain.usecaseimpl.GetUsersUseCaseImpl
import org.koin.dsl.module

internal fun domainModule() = module {
    factory<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
    factory<GetUserDetailUseCase> { GetUserDetailUseCaseImpl(get()) }
}
