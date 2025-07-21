package com.varosyan.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_details")
data class UserDetailEntity(
    @PrimaryKey val id: Int,
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