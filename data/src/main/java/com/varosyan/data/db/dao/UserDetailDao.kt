package com.varosyan.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varosyan.data.db.entity.UserDetailEntity

@Dao
interface UserDetailDao {
    @Query("SELECT * FROM user_details WHERE userName = :userName")
    suspend fun getById(userName: String): UserDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(detail: UserDetailEntity)
}