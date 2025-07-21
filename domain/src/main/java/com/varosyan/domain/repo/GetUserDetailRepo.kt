package com.varosyan.domain.repo

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.UserDetail

interface GetUserDetailRepo {
    suspend operator fun invoke(username: String): Result<UserDetail>
}