package com.varosyan.data.model

import com.google.gson.annotations.SerializedName
import com.varosyan.domain.model.User

data class UserModel(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("login") val username: String
)

fun UserModel.toUser() = User(id, avatar, username)