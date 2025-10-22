package com.varosyan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailModel(
    @SerialName("id") val id: Long,
    @SerialName("avatar_url") val avatar: String,
    @SerialName("login") val username: String,
    @SerialName("name") val fullName: String?,
    @SerialName("location") val location: String?,
    @SerialName("followers") val followersCount: Int,
    @SerialName("following") val followingCount: Int,
    @SerialName("bio") val bio: String?,
    @SerialName("public_repos") val repoCount: Int?,
    @SerialName("public_gists") val gistCount: Int?,
    @SerialName("updated_at") val lastUpdated: String?
)