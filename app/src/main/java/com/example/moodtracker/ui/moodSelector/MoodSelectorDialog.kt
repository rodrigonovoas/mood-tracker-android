package com.example.moodtracker.ui.moodSelector

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        const val HAPPY_MOOD_TEXT = "Happy :)"
        const val NEUTRAL_MOOD_TEXT = "Neutral :l"
        const val SAD_MOOD_TEXT = "Sad :("
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

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.selectedMoodText.observe(this, Observer { mood ->
            binding.tvMoodDescription.visibility = View.VISIBLE
            binding.tvMoodDescription.setText(mood)

            setImageviewBackgroundColor(mood)
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
            {
                sharedPrefs.setLastMoodDate(DateUtils.getCurrentDateTimeAsTimeStamp())
                loadMoodsAndDissmiss(false)
            }
        })
    }

    private fun setListeners() {
        binding.imvClose.setOnClickListener { loadMoodsAndDissmiss(true)}
        binding.llComment.setOnClickListener { viewModel.setCommentVisibility() }
        binding.imvHappyMood.setOnClickListener { viewModel.setCurrentMood(HAPPY_MOOD) }
        binding.imvNeutralMood.setOnClickListener { viewModel.setCurrentMood(NEUTRAL_MOOD) }
        binding.imvSadMood.setOnClickListener { viewModel.setCurrentMood(SAD_MOOD) }
        binding.btnContinue.setOnClickListener { addMoodIfSelected() }
    }

    private fun addMoodIfSelected() {
        if (!viewModel.hasAnyMoodBeenSelected())
            return

        viewModel.addMoodToDatabase(
            Mood(null,
                DateUtils.getCurrentDateTimeAsTimeStamp(),
                viewModel.getSelectedMood(),
                binding.edtComment.text.toString())
        )
    }

    private fun loadMoodsAndDissmiss(moodButtonVisible: Boolean) {
        if (activity is MoodTrackerActivity)
        {
            (activity as MoodTrackerActivity).getMoods()
            (activity as MoodTrackerActivity).addMoodButtonVisibility(moodButtonVisible)
        }

        this.dismiss()
    }

    private fun setImageviewBackgroundColor(mood: String) {
        binding.imvHappyMood.background = null
        binding.imvSadMood.background = null
        binding.imvNeutralMood.background = null

        when (mood) {
            MoodSelectorDialog.HAPPY_MOOD_TEXT -> setMoodTextColor(R.color.happiness_status)
            MoodSelectorDialog.NEUTRAL_MOOD_TEXT -> setMoodTextColor(R.color.neutral_status)
            MoodSelectorDialog.SAD_MOOD_TEXT -> setMoodTextColor(R.color.sad_status)
            else -> setMoodTextColor(R.color.neutral_status)
        }

        when (mood) {
            MoodSelectorDialog.HAPPY_MOOD_TEXT
                -> setImageviewBackground(binding.imvHappyMood, R.drawable.bg_happy_mood_selected)
            MoodSelectorDialog.NEUTRAL_MOOD_TEXT
                -> setImageviewBackground(binding.imvNeutralMood, R.drawable.bg_neutral_mood_selected)
            MoodSelectorDialog.SAD_MOOD_TEXT
                -> setImageviewBackground(binding.imvSadMood, R.drawable.bg_sad_mood_selected)
            else -> setImageviewBackground(binding.imvNeutralMood, R.drawable.bg_neutral_mood_selected)
        }
    }

    private fun setMoodTextColor(colorId: Int) {
        binding.tvMoodDescription.setTextColor(requireContext().getColor(colorId))
    }

    private fun setImageviewBackground(imv: ImageView, drawableId: Int) {
        imv.background = requireContext().getDrawable(drawableId)

    }

}