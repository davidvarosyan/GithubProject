package com.varosyan.domain.usecase

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.User

interface GetUsersUseCase {
    suspend operator fun invoke(id: Long):Result<List<User>>
}