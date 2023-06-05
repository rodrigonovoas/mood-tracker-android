package com.example.moodtracker.ui.moodSelector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.moodtracker.R
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.MoodDatabaseRepository
import com.example.moodtracker.ui.moodTracker.MoodTrackerActivity
import com.example.moodtracker.utils.DateUtils

class MoodSelectorDialog: DialogFragment() {

    companion object{
        val HAPPY_MOOD = 0
        val NEUTRAL_MOOD = 1
        val SAD_MOOD = 2

        val HAPPY_MOOD_TEXT = "Happy"
        val NEUTRAL_MOOD_TEXT = "Neutral"
        val SAD_MOOD_TEXT = "Sad"
    }

    private lateinit var viewModel: MoodSelectorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_mood_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = MoodSelectorViewModel(MoodDatabaseRepository(requireContext()))

        setListeners(view)
        setObservers(view)
    }

    private fun setObservers(view: View) {
        viewModel.selectedMoodText.observe(this, Observer { mood ->
            val tvMood = view.findViewById<TextView>(R.id.tv_mood_description)
            tvMood.setText(mood)
        })

        viewModel.edtCommentVisibility.observe(this, Observer { visible ->
            val edtComment = view.findViewById<EditText>(R.id.edt_comment)

            if (visible) {
                edtComment.visibility = View.VISIBLE
            } else {
                edtComment.visibility = View.GONE
            }
        })

        viewModel.closeDialog.observe(this, Observer { close ->
            if (close) {
                if (activity is MoodTrackerActivity)
                        (activity as MoodTrackerActivity).getMoods()
                this.dismiss()
            }
        })
    }

    private fun setListeners(view: View) {
        val llComment = view.findViewById<LinearLayout>(R.id.ll_comment)
        llComment.setOnClickListener { viewModel.setCommentVisibility() }

        val imvHappy = view.findViewById<ImageView>(R.id.imv_happy_mood)
        imvHappy.setOnClickListener { viewModel.setCurrentMood(HAPPY_MOOD) }

        val imvNeutral = view.findViewById<ImageView>(R.id.imv_neutral_mood)
        imvNeutral.setOnClickListener { viewModel.setCurrentMood(NEUTRAL_MOOD) }

        val imvSad = view.findViewById<ImageView>(R.id.imv_sad_mood)
        imvSad.setOnClickListener { viewModel.setCurrentMood(SAD_MOOD) }

        val btnContinue = view.findViewById<Button>(R.id.btn_continue)
        btnContinue.setOnClickListener {
            addMoodIfSelected(view)
        }
    }

    private fun addMoodIfSelected(view: View) {
        if (!viewModel.hasAnyMoodBeenSelected())
            return

        val edtComment = view.findViewById<EditText>(R.id.edt_comment)

        viewModel.addMoodToDatabase(
            Mood(null,
                DateUtils.getCurrentDateTimeAsTimeStamp(),
                viewModel.getSelectedMood(),
                edtComment.text.toString()
            )
        )
    }

}