package com.varosyan.data.service

import com.varosyan.data.model.UserDetailModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class UserDetailApiServiceImpl @Inject constructor(private val client: HttpClient) : UserDetailApiService {

    override suspend fun getUserDetail(username: String): UserDetailModel {
        return client.get("users/$username").body()
    }
}