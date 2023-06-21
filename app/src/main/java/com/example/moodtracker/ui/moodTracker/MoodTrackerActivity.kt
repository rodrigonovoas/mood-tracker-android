package com.example.moodtracker.ui.moodTracker

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodtracker.R
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.databinding.ActivityMoodTrackerBinding
import com.example.moodtracker.db.MoodDatabaseRepository
import com.example.moodtracker.sharedPreferences.SharedPreferenceHelper
import com.example.moodtracker.ui.moodComment.MoodCommentDialog
import com.example.moodtracker.ui.moodSelector.MoodSelectorDialog
import com.example.moodtracker.utils.DateUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*


class MoodTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoodTrackerBinding
    private lateinit var viewModel: MoodTrackerViewModel
    private lateinit var sharedPreferences: SharedPreferenceHelper
    private lateinit var moodSelectorDialog: MoodSelectorDialog
    private lateinit var moodCommentDialog: MoodCommentDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferenceHelper(this)
        viewModel = MoodTrackerViewModel(MoodDatabaseRepository(this))
        moodSelectorDialog = MoodSelectorDialog()

        openMoodSelectorDialog()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.btnAddMood.setOnClickListener { moodSelectorDialog.show(supportFragmentManager, "MoodSelectorDialog") }
        binding.btnAddComment.setOnClickListener { moodCommentDialog.show(supportFragmentManager, "MoodCommentDialog") }
        binding.imvInfo.setOnClickListener { showBottomSheetDialog() }
    }

    private fun openMoodSelectorDialog() {
        if (!hasTodayMoodBeenAdded()) {
            moodSelectorDialog.show(supportFragmentManager, "MoodSelectorDialog")
        } else {
            binding.btnAddMood.visibility = View.GONE
            getMoods()
        }
    }

    private fun setObservers() {
        viewModel.moods.observe(this, Observer { moods ->
            loadMoodsAndSetLastOne(moods)
        })

        viewModel.selectedMood.observe(this, Observer { mood ->
            moodCommentDialog = MoodCommentDialog(mood)

            val addButtonVisible = (mood.comment != null && mood.comment.isEmpty())
            viewModel.setAddMoodVisibility(addButtonVisible)

            setMoodDataInScreen(mood)
        })

        viewModel.btnAddMoodVisibility.observe(this, Observer { visible ->
            if(visible) {
                binding.btnAddComment.visibility = View.VISIBLE
            } else {
                binding.btnAddComment.visibility = View.GONE
            }
        })
    }

    private fun loadMoodsAndSetLastOne(moods: List<Mood>) {
        if (moods.isEmpty()) return
        val adapter = MoodListAdapter(moods)

        adapter.onMoodClick = {
            viewModel.setSelectedMood(it)
        }

        binding.rcMood.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false)

        binding.rcMood.adapter = adapter

        // scroll to last mood and set its data
        val lastPosition = moods.size - 1
        binding.rcMood.scrollToPosition(lastPosition)
        moodCommentDialog = MoodCommentDialog(moods[lastPosition])
        setMoodDataInScreen(moods[lastPosition])
    }

    private fun hasTodayMoodBeenAdded(): Boolean {
        val lastStoredMoodDate = sharedPreferences.getLastMoodDate()
        val currentDate = SimpleDateFormat("dd", Locale.FRANCE).format(DateUtils.getCurrentDateTimeAsTimeStamp())
        val lastMoodDate = SimpleDateFormat("dd", Locale.FRANCE).format(lastStoredMoodDate)

        return currentDate.equals(lastMoodDate)
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
        binding.tvMoodComment.text = ""
        if (mood.comment != null && mood.comment.isNotEmpty()) binding.tvMoodComment.setText("\"" + mood.comment + "\"")
        binding.tvMoodDate.setText(DateUtils.convertLongToLongDate(mood.creationDate))
        binding.imvMoodStatus.setImageDrawable(getDrawable(R.drawable.ic_happy_mood))
        binding.mainView.setBackgroundColor(getColor(R.color.happiness_status))
    }

    private fun setNeutralMoodScreen(mood: Mood) {
        binding.tvMoodComment.text = ""
        if (mood.comment != null && mood.comment.isNotEmpty()) binding.tvMoodComment.setText("\" " + mood.comment + " \"")
        binding.tvMoodDate.setText(DateUtils.convertLongToLongDate(mood.creationDate))
        binding.imvMoodStatus.setImageDrawable(getDrawable(R.drawable.ic_neutral_mood))
        binding.mainView.setBackgroundColor(getColor(R.color.neutral_status))
    }

    private fun setSadMoodScreen(mood: Mood) {
        binding.tvMoodComment.text = ""
        if (mood.comment != null && mood.comment.isNotEmpty()) binding.tvMoodComment.setText("\" " + mood.comment + " \"")
        binding.tvMoodDate.setText(DateUtils.convertLongToLongDate(mood.creationDate))
        binding.imvMoodStatus.setImageDrawable(getDrawable(R.drawable.ic_sad_mood))
        binding.mainView.setBackgroundColor(getColor(R.color.sad_status))
    }

    fun getMoods() {
        viewModel.getMoods()
    }

    fun addMoodButtonVisibility(visible: Boolean) {
        if (visible) {
            binding.btnAddMood.visibility = View.VISIBLE
        } else {
            binding.btnAddMood.visibility = View.GONE
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.dialog_info)

        val tvHappyMoods = bottomSheetDialog.findViewById<TextView>(R.id.tv_happy_moods)
        val tvNeutralMoods = bottomSheetDialog.findViewById<TextView>(R.id.tv_neutral_moods)
        val tvSadMoods = bottomSheetDialog.findViewById<TextView>(R.id.tv_sad_moods)
        val btnClose = bottomSheetDialog.findViewById<MaterialButton>(R.id.btn_close)

        tvHappyMoods?.setText(tvHappyMoods?.text.toString() + " »  " + viewModel.getHappyMoodQuantity())
        tvNeutralMoods?.setText(tvNeutralMoods?.text.toString() + " »  " + viewModel.getNeutralMoodQuantity())
        tvSadMoods?.setText(tvSadMoods?.text.toString() + " »  " + viewModel.getSadMoodQuantity())

        btnClose?.setOnClickListener { bottomSheetDialog.dismiss() }

        bottomSheetDialog.show()
    }
}