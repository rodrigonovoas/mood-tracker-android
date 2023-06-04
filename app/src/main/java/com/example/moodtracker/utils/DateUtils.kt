package com.example.moodtracker.utils

object DateUtils {
    fun getCurrentDateTimeAsTimeStamp(): Long {
        val currentDateTime: java.util.Date = java.util.Date()
        return currentDateTime.time
    }
}