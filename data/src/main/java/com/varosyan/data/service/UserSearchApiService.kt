package com.varosyan.data.service

import com.varosyan.data.model.UserSearchModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSearchApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String, @Query("per_page") perPage: Int, @Query("page") page: Int
    ): UserSearchModel
}