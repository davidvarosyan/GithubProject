package com.varosyan.data.service

import com.varosyan.data.model.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class UserApiServiceImpl @Inject constructor(private val client: HttpClient) : UserApiService {
    override suspend fun getUsers(since: Long, perPage: Int): List<UserModel> {
        return client.get("users") {
            parameter("since", since)
            parameter("per_page", perPage)
        }.body()
    }

}