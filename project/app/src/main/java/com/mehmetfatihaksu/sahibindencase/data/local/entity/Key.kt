package com.mehmetfatihaksu.sahibindencase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Keys")
data class Key(@PrimaryKey val Id: Int, val prevKey :Int? ,val nextKey :Int?)
