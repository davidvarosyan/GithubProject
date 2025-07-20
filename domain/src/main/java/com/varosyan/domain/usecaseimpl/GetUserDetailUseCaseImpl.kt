package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.repo.GetUserDetailRepo
import com.varosyan.domain.usecase.GetUserDetailUseCase

class GetUserDetailUseCaseImpl(private val getUserDetailRepo: GetUserDetailRepo) :
    GetUserDetailUseCase {
    override suspend fun invoke(userName: String): UserDetail = getUserDetailRepo(userName)
}