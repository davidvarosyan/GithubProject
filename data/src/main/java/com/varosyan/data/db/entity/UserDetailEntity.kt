package com.varosyan.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_details")
data class UserDetailEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "avatar") val avatar: String,
    @ColumnInfo(name = "userName")val userName: String,
    @ColumnInfo(name = "fullName")val fullName: String?,
    @ColumnInfo(name = "location")val location: String?,
    @ColumnInfo(name = "followersCount")val followersCount: Int,
    @ColumnInfo(name = "followingCount")val followingCount: Int,
    @ColumnInfo(name = "bio")val bio: String?,
    @ColumnInfo(name = "repoCount")val repoCount: Int?,
    @ColumnInfo(name = "gistCount")val gistCount: Int?,
    @ColumnInfo(name = "lastUpdated")val lastUpdated: String?
)