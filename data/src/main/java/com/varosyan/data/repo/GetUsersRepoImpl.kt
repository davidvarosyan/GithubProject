package com.varosyan.data.repo

import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.entity.UserEntity
import com.varosyan.data.model.toUser
import com.varosyan.data.service.UserApiService
import com.varosyan.domain.common.Result
import com.varosyan.domain.common.safeCall
import com.varosyan.domain.model.User
import com.varosyan.domain.repo.GetUsersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUsersRepoImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) :
    GetUsersRepo {
    override suspend fun invoke(id: Long): Result<List<User>> {
        return safeCall {
            val users = userApiService.getUsers(id, 50).map { it.toUser() }
            return@safeCall users
        }.fold(
            onSuccess = { users ->
                coroutineScope {
                    launch(Dispatchers.IO) {
                        userDao.upsertAll(users.map { UserEntity(it.id, it.userName, it.avatar) })
                    }
                }
                Result.Success(users)
            },
            onError = {
                safeCall {
                    userDao.getUsersFromId(startId = id, count = 50)
                        .map { entity -> User(entity.id, entity.avatarUrl, entity.userName) }
                }
            })
    }

}