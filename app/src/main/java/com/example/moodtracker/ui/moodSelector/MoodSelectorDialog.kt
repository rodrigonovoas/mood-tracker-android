package com.example.moodtracker.ui.moodSelector

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.moodtracker.R
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.databinding.DialogMoodSelectorBinding
import com.example.moodtracker.db.MoodDatabaseRepository
import com.example.moodtracker.sharedPreferences.SharedPreferenceHelper
import com.example.moodtracker.ui.moodTracker.MoodTrackerActivity
import com.example.moodtracker.utils.DateUtils


class MoodSelectorDialog: DialogFragment() {
    private lateinit var binding: DialogMoodSelectorBinding
    private lateinit var sharedPrefs: SharedPreferenceHelper

    companion object{
        const val HAPPY_MOOD = 0
        const val NEUTRAL_MOOD = 1
        const val SAD_MOOD = 2

        const val HAPPY_MOOD_TEXT = "Happy"
        const val NEUTRAL_MOOD_TEXT = "Neutral"
        const val SAD_MOOD_TEXT = "Sad"
    }

    private lateinit var viewModel: MoodSelectorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogMoodSelectorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefs = SharedPreferenceHelper(requireContext())
        viewModel = MoodSelectorViewModel(MoodDatabaseRepository(requireContext()))

        setListeners(view)
        setObservers(view)
    }

    private fun setObservers(view: View) {
        viewModel.selectedMoodText.observe(this, Observer { mood ->
            binding.tvMoodDescription.setText(mood)
        })

        viewModel.edtCommentVisibility.observe(this, Observer { visible ->
            if (visible) {
                binding.edtComment.visibility = View.VISIBLE
            } else {
                binding.edtComment.visibility = View.GONE
            }
        })

        viewModel.closeDialog.observe(this, Observer { close ->
            if (close)
                sharedPrefs.setLastMoodDate(DateUtils.getCurrentDateTimeAsTimeStamp())
                loadMoodsAndDissmiss(false)
        })
    }

    private fun setListeners(view: View) {
        binding.imvClose.setOnClickListener { loadMoodsAndDissmiss(true)}
        binding.llComment.setOnClickListener { viewModel.setCommentVisibility() }
        binding.imvHappyMood.setOnClickListener { viewModel.setCurrentMood(HAPPY_MOOD) }
        binding.imvNeutralMood.setOnClickListener { viewModel.setCurrentMood(NEUTRAL_MOOD) }
        binding.imvSadMood.setOnClickListener { viewModel.setCurrentMood(SAD_MOOD) }
        binding.btnContinue.setOnClickListener { addMoodIfSelected(view) }
    }

    private fun addMoodIfSelected(view: View) {
        if (!viewModel.hasAnyMoodBeenSelected())
            return

        val edtComment = view.findViewById<EditText>(R.id.edt_comment)

        viewModel.addMoodToDatabase(
            Mood(null,
                DateUtils.getCurrentDateTimeAsTimeStamp(),
                viewModel.getSelectedMood(),
                edtComment.text.toString())
        )
    }

    private fun loadMoodsAndDissmiss(moodButtonVisible: Boolean) {
        if (activity is MoodTrackerActivity)
        {
            (activity as MoodTrackerActivity).getMoods()
            if (moodButtonVisible) (activity as MoodTrackerActivity).makeAddMoodButtonVisible()
        }

        this.dismiss()
    }

}