package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.repo.GetUserDetailRepo
import com.varosyan.domain.usecase.GetUserDetailUseCase
import javax.inject.Inject

class GetUserDetailUseCaseImpl @Inject constructor(private val getUserDetailRepo: GetUserDetailRepo) :
    GetUserDetailUseCase {
    override suspend fun invoke(userName: String): Result<UserDetail> = getUserDetailRepo(userName)
}