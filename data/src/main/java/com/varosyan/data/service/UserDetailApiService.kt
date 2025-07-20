package com.varosyan.data.service

import com.varosyan.data.model.UserDetailModel
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailApiService {
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserDetailModel
}