package com.mehmetfatihaksu.sahibindencase.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehmetfatihaksu.sahibindencase.data.local.dao.KeyDao
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key

@Database(entities = [Key::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun KeyDao() : KeyDao
}