package com.mehmetfatihaksu.sahibindencase.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key

@Dao
interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeys(keys : List<Key>)

    @Query("SELECT * FROM Keys where Id=:Id")
    fun getKey(Id : Int) : Key?

    @Query("DELETE FROM Keys")
    fun clearKeys()
}