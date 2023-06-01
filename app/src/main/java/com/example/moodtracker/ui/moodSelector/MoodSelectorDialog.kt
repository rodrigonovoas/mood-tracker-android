package com.example.moodtracker.ui.moodSelector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.moodtracker.R

class MoodSelectorDialog: DialogFragment() {

    companion object{
        val HAPPY_MOOD = 0
        val NEUTRAL_MOOD = 1
        val SAD_MOOD = 2

        val HAPPY_MOOD_TEXT = "Happy"
        val NEUTRAL_MOOD_TEXT = "Neutral"
        val SAD_MOOD_TEXT = "Sad"
    }

    private val viewModel: MoodSelectorViewModel = MoodSelectorViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_mood_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    private fun setListeners(view: View) {
        val tvMood = view.findViewById<TextView>(R.id.tv_comment_title)
        tvMood.setOnClickListener { viewModel.setCommentVisibility() }

        val imvHappy = view.findViewById<ImageView>(R.id.imv_happy_mood)
        imvHappy.setOnClickListener { viewModel.setCurrentMood(HAPPY_MOOD) }

        val imvNeutral = view.findViewById<ImageView>(R.id.imv_neutral_mood)
        imvNeutral.setOnClickListener { viewModel.setCurrentMood(NEUTRAL_MOOD) }

        val imvSad = view.findViewById<ImageView>(R.id.imv_sad_mood)
        imvSad.setOnClickListener { viewModel.setCurrentMood(SAD_MOOD) }

        val btnContinue = view.findViewById<Button>(R.id.btn_continue)
        btnContinue.setOnClickListener {
            if (!viewModel.hasAnyMoodBeenSelected()) return@setOnClickListener
        }
    }

}