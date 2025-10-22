package com.varosyan.data.model

import com.varosyan.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("id") val id: Long,
    @SerialName("avatar_url") val avatar: String,
    @SerialName("login") val username: String
)

fun UserModel.toUser() = User(id, avatar, username)