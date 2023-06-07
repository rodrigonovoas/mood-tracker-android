package com.example.moodtracker.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getCurrentDateTimeAsTimeStamp(): Long {
        val currentDateTime: java.util.Date = java.util.Date()
        return currentDateTime.time
    }

    fun convertLongToShortDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd-MM")
        return format.format(date)
    }

    fun convertLongToLongDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(date)
    }

}