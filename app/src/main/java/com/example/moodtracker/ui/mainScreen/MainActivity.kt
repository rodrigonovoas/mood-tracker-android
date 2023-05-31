package com.example.moodtracker.ui.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moodtracker.R
import com.example.moodtracker.ui.moodSelector.MoodSelectorDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openMoodSelectorDialog()
    }

    private fun openMoodSelectorDialog() {
        MoodSelectorDialog().show(supportFragmentManager, "MoodSelectorDialog")
    }
}