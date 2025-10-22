package com.varosyan.data.service

import com.varosyan.data.model.UserModel

interface UserApiService {
    suspend fun getUsers(since: Long, perPage: Int): List<UserModel>
}