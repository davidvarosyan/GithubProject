package com.varosyan.data.service

import com.varosyan.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Long,
        @Query("per_page") perPage: Int
    ): List<UserModel>
}