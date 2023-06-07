package com.example.moodtracker.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getCurrentDateTimeAsTimeStamp(): Long {
        val currentDateTime: java.util.Date = java.util.Date()
        return currentDateTime.time
    }

    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd-MM")
        return format.format(date)
    }

}