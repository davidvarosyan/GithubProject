package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.User
import com.varosyan.domain.repo.GetUsersRepo
import com.varosyan.domain.usecase.GetUsersUseCase
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(private val getUsersRepo: GetUsersRepo) : GetUsersUseCase {
    override suspend fun invoke(id: Long): Result<List<User>> = getUsersRepo(id)
}