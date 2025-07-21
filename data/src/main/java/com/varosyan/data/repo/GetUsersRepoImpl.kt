package com.varosyan.data.repo

import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.entity.UserEntity
import com.varosyan.data.model.toUser
import com.varosyan.data.service.UserApiService
import com.varosyan.domain.model.User
import com.varosyan.domain.repo.GetUsersRepo

class GetUsersRepoImpl(private val userApiService: UserApiService, private val userDao: UserDao) :
    GetUsersRepo {
    override suspend fun invoke(id: Long): List<User> {

        val users = userApiService.getUsers(id, 50).map { it.toUser() }
        userDao.upsertAll(users.map { UserEntity(it.id, it.avatar, it.userName) })
        return users
    }

}