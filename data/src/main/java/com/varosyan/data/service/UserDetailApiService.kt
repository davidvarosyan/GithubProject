package com.varosyan.data.service

import com.varosyan.data.model.UserDetailModel

interface UserDetailApiService {
    suspend fun getUserDetail(username:String): UserDetailModel
}