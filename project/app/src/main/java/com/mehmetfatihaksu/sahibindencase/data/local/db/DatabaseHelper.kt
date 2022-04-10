package com.mehmetfatihaksu.sahibindencase.data.local.db

import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key

interface DatabaseHelper {

    fun insertKeys(keys : List<Key>)

    fun getKey(Id : Int) : Key?

    fun clearKeys()
}