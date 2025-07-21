package com.varosyan.data.db

import android.content.Context
import androidx.room.Room

fun getDatabase(context: Context) = Room.databaseBuilder(
    context, AppDatabase::class.java, "app_db"
).fallbackToDestructiveMigration(false).build()