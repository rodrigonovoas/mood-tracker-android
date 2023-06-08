package com.example.moodtracker.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {

    private val sharedPreferencesKey = "MoodTrackerSharedPreferences"
    private val lastMoodDateKey = "lastMoodDateKey"

    private var sharedPreferences: SharedPreferences

    constructor(context: Context) {
        this.sharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
    }

    fun getLastMoodDate(): Long {
        return sharedPreferences.getLong(lastMoodDateKey, 0L)
    }

    fun setLastMoodDate(value: Long) {
        with (sharedPreferences.edit()) {
            putLong(lastMoodDateKey, value)
            apply()
        }
    }
}