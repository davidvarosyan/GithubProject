package com.varosyan.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "avatar") val avatarUrl: String
)