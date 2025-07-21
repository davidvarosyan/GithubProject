package com.varosyan.data.repo

import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.db.entity.UserDetailEntity
import com.varosyan.data.model.toUserDetail
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.repo.GetUserDetailRepo

class GetUserDetailRepoImpl(
    private val userDetailApiService: UserDetailApiService,
    private val userDetailDao: UserDetailDao
) : GetUserDetailRepo {
    override suspend fun invoke(username: String): UserDetail {
        val result = userDetailApiService.getUserDetail(username).toUserDetail()
        userDetailDao.upsert(
            UserDetailEntity(
                result.id,
                result.avatar,
                result.userName,
                result.fullName,
                result.location,
                result.followersCount,
                result.followingCount,
                result.bio,
                result.repoCount,
                result.gistCount,
                result.lastUpdated
            )
        )
        return result
    }
}