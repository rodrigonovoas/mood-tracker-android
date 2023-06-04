package com.example.moodtracker.ui.moodTracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moodtracker.R
import com.example.moodtracker.ui.moodSelector.MoodSelectorDialog

class MoodTrackerActivity : AppCompatActivity() {
    private val moodSelectorDialog = MoodSelectorDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_tracker)

        openMoodSelectorDialog()
    }

    private fun openMoodSelectorDialog() {
        moodSelectorDialog.show(supportFragmentManager, "MoodSelectorDialog")
    }
}