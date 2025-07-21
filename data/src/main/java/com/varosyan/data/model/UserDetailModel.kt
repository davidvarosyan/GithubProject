package com.varosyan.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailModel(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("login") val username: String,
    @SerializedName("name") val fullName: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("followers") val followersCount: Int,
    @SerializedName("following") val followingCount: Int,
    @SerializedName("bio") val bio: String?,
    @SerializedName("public_repos") val repoCount: Int?,
    @SerializedName("public_gists") val gistCount: Int?,
    @SerializedName("updated_at") val lastUpdated: String?
)