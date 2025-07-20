package com.varosyan.data.repo

import com.varosyan.data.model.toUserDetail
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.repo.GetUserDetailRepo

class GetUserDetailRepoImpl(val userDetailApiService: UserDetailApiService) : GetUserDetailRepo {
    override suspend fun invoke(username: String): UserDetail {
        return userDetailApiService.getUserDetail(username).toUserDetail()
    }
}