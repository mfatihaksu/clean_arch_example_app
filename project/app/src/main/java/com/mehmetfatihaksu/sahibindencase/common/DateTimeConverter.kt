package com.mehmetfatihaksu.sahibindencase.common

import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter {

    companion object {
        fun getDateTime(s: Int, format : String): String? {
            return try {
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                val netDate = Date(s.toLong() * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }
    }
}