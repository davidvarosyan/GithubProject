package com.varosyan.data.repo

import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.mapper.UserDetailMapper
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.domain.common.Result
import com.varosyan.domain.common.safeCall
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.repo.GetUserDetailRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUserDetailRepoImpl @Inject constructor(
    private val userDetailApiService: UserDetailApiService,
    private val userDetailDao: UserDetailDao,
    private val userDetailMapper: UserDetailMapper
) : GetUserDetailRepo {
    override suspend fun invoke(username: String): Result<UserDetail> {
        return safeCall {
            userDetailMapper.userDetailModelToUserDetail(userDetailApiService.getUserDetail(username))
        }.fold(onError = {
            return safeCall {
                userDetailMapper.userDetailEntityToUserDetail(
                    userDetailDao.getById(
                        userName = username
                    )
                )
            }
        }, onSuccess = { result ->
            coroutineScope {
                launch(Dispatchers.IO) {
                    userDetailDao.upsert(
                        userDetailMapper.userDetailToUserDetailEntity(result)
                    )
                }
            }
            return Result.Success(result)
        })
    }
}