package com.varosyan.domain.model

data class UserDetail(
    val id: Int,
    val avatar: String,
    val userName: String,
    val fullName: String?,
    val location: String?,
    val followersCount: Int,
    val followingCount: Int,
    val bio: String?,
    val repoCount: Int?,
    val gistCount: Int?,
    val lastUpdated: String?
)