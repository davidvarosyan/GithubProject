package com.varosyan.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varosyan.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Query(
        """
    SELECT * 
    FROM users 
    WHERE id >= :startId 
    ORDER BY id ASC 
    LIMIT :count
  """
    )
    suspend fun getUsersFromId(
        startId: Int,
        count: Int
    ): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}