package com.varosyan.domain.usecase

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.UserDetail

interface GetUserDetailUseCase {
    suspend operator fun invoke(userName: String): Result<UserDetail>
}