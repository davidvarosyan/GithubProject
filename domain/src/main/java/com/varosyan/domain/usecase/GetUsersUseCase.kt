package com.varosyan.domain.usecase

import com.varosyan.domain.model.User

interface GetUsersUseCase {
    suspend operator fun invoke(id: Long): List<User>
}