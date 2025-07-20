package com.varosyan.data.repo

import com.varosyan.data.model.toUser
import com.varosyan.data.service.UserApiService
import com.varosyan.domain.model.User
import com.varosyan.domain.repo.GetUsersRepo

class GetUsersRepoImpl(val userApiService: UserApiService) : GetUsersRepo {
    override suspend fun invoke(id: Long): List<User> =
        userApiService.getUsers(id, 50).map { it.toUser() }
}