package com.example.moodtracker

import android.app.Application
import com.example.moodtracker.db.MoodDatabase

class MoodTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MoodDatabase.createDatabase(this)
    }
}