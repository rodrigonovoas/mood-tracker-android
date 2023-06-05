package com.example.moodtracker.ui.moodTracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.db.MoodDatabaseRepository
import com.example.moodtracker.ui.moodSelector.MoodListAdapter
import com.example.moodtracker.ui.moodSelector.MoodSelectorDialog

class MoodTrackerActivity : AppCompatActivity() {
    private lateinit var viewModel: MoodTrackerViewModel
    private lateinit var moodSelectorDialog: MoodSelectorDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_tracker)

        viewModel = MoodTrackerViewModel(MoodDatabaseRepository(this))
        moodSelectorDialog = MoodSelectorDialog()

        openMoodSelectorDialog()
        setObservers()
    }

    private fun setObservers() {
        viewModel.moods.observe(this, Observer { moods ->
            val rcMoods = findViewById<RecyclerView>(R.id.rc_mood)
            val adapter = MoodListAdapter(moods)

            rcMoods.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            rcMoods.adapter = adapter
        })
    }

    private fun openMoodSelectorDialog() {
        moodSelectorDialog.show(supportFragmentManager, "MoodSelectorDialog")
    }

    fun getMoods() {
        viewModel.getMoods()
    }
}