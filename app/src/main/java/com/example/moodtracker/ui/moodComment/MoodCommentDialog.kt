package com.example.moodtracker.ui.moodComment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.databinding.DialogMoodCommentBinding
import com.example.moodtracker.db.MoodDatabaseRepository
import com.example.moodtracker.ui.moodTracker.MoodTrackerActivity

class MoodCommentDialog(mood: Mood): DialogFragment() {
    private lateinit var binding: DialogMoodCommentBinding
    private lateinit var viewModel: MoodCommentViewModel
    private var oldMood: Mood

    init {
        this.oldMood = mood
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogMoodCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = MoodCommentViewModel(MoodDatabaseRepository(requireContext()))

        setObservers()
        setViewListeners()
    }

    private fun setViewListeners() {
        binding.imvClose.setOnClickListener { this.dismiss() }

        binding.btnContinue.setOnClickListener {
            viewModel.updateCommentary(this.oldMood, binding.edtMoodComment.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.moodUpdated.observe(this, Observer { updated ->
            if (updated) {
                if (activity is MoodTrackerActivity) { (activity as MoodTrackerActivity).getMoods() }
                this.dismiss()
            }
        })
    }

}