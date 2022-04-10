package com.mehmetfatihaksu.sahibindencase.data.local.db

import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun insertKeys(keys: List<Key>) {
        appDatabase.KeyDao().insertKeys(keys)
    }

    override fun getKey(Id: Int): Key? {
        return appDatabase.KeyDao().getKey(Id)
    }

    override fun clearKeys() {
        appDatabase.KeyDao().clearKeys()
    }
}