package com.varosyan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.db.entity.UserDetailEntity
import com.varosyan.data.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, UserDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
}