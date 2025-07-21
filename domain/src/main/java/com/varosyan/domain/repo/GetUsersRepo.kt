package com.varosyan.domain.repo

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.User

interface GetUsersRepo {
    suspend operator fun invoke(id: Long): Result<List<User>>
}