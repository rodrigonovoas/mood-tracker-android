package com.example.moodtracker.ui.moodTracker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.MoodDatabaseRepository
import com.example.moodtracker.ui.moodSelector.MoodListAdapter
import com.example.moodtracker.ui.moodSelector.MoodSelectorDialog
import com.example.moodtracker.utils.DateUtils

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
            loadMoodsAndSetLastOne(moods)
        })
    }

    private fun loadMoodsAndSetLastOne(moods: List<Mood>) {
        if (moods.isEmpty()) return

        val rcMoods = findViewById<RecyclerView>(R.id.rc_mood)
        val adapter = MoodListAdapter(moods)

        adapter.onMoodClick = {
            setMoodDataInScreen(it)
        }

        rcMoods.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rcMoods.adapter = adapter

        // scroll to last mood and set its data
        val lastPosition = moods.size - 1
        rcMoods.scrollToPosition(lastPosition)
        setMoodDataInScreen(moods[lastPosition])
    }

    private fun openMoodSelectorDialog() {
        moodSelectorDialog.show(supportFragmentManager, "MoodSelectorDialog")
    }

    private fun setMoodDataInScreen(mood: Mood) {
        when (mood.moodType) {
            MoodSelectorDialog.HAPPY_MOOD -> setHappyMoodScreen(mood)
            MoodSelectorDialog.NEUTRAL_MOOD -> setNeutralMoodScreen(mood)
            MoodSelectorDialog.SAD_MOOD -> setSadMoodScreen(mood)
            else -> setNeutralMoodScreen(mood)
        }
    }

    private fun setHappyMoodScreen(mood: Mood) {
        val tvComment = findViewById<TextView>(R.id.tv_mood_comment)
        val tvDate = findViewById<TextView>(R.id.tv_mood_date)
        val imvMood = findViewById<ImageView>(R.id.imv_mood_status)
        val screenBackground = findViewById<ConstraintLayout>(R.id.main_view)

        tvComment.setText("\"" + mood.comment + "\"")
        tvDate.setText(DateUtils.convertLongToLongDate(mood.creationDate))
        imvMood.setImageDrawable(getDrawable(R.drawable.ic_happy_mood))
        screenBackground.setBackgroundColor(getColor(R.color.happiness_status))
    }

    private fun setNeutralMoodScreen(mood: Mood) {
        val tvComment = findViewById<TextView>(R.id.tv_mood_comment)
        val tvDate = findViewById<TextView>(R.id.tv_mood_date)
        val imvMood = findViewById<ImageView>(R.id.imv_mood_status)
        val screenBackground = findViewById<ConstraintLayout>(R.id.main_view)

        tvComment.setText("\"" + mood.comment + "\"")
        tvDate.setText(DateUtils.convertLongToLongDate(mood.creationDate))
        imvMood.setImageDrawable(getDrawable(R.drawable.ic_neutral_mood))
        screenBackground.setBackgroundColor(getColor(R.color.neutral_status))
    }

    private fun setSadMoodScreen(mood: Mood) {
        val tvComment = findViewById<TextView>(R.id.tv_mood_comment)
        val tvDate = findViewById<TextView>(R.id.tv_mood_date)
        val imvMood = findViewById<ImageView>(R.id.imv_mood_status)
        val screenBackground = findViewById<ConstraintLayout>(R.id.main_view)

        tvComment.setText("\"" + mood.comment + "\"")
        tvDate.setText(DateUtils.convertLongToLongDate(mood.creationDate))
        imvMood.setImageDrawable(getDrawable(R.drawable.ic_sad_mood))
        screenBackground.setBackgroundColor(getColor(R.color.sad_status))
    }

    fun getMoods() {
        viewModel.getMoods()
    }
}