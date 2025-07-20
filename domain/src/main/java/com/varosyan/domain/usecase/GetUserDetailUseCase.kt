package com.varosyan.domain.usecase

import com.varosyan.domain.model.UserDetail

interface GetUserDetailUseCase {
    suspend fun invoke(userName: String): UserDetail
}