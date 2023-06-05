package com.example.moodtracker.ui.moodSelector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.MoodDatabaseRepository
import kotlinx.coroutines.launch

class MoodSelectorViewModel(val repository: MoodDatabaseRepository): ViewModel() {

    val selectedMoodText: LiveData<String> get() = _selectedMoodText
    private var _selectedMoodText = MutableLiveData<String>()

    val edtCommentVisibility: LiveData<Boolean> get() = _edtCommentVisibility
    private var _edtCommentVisibility = MutableLiveData<Boolean>()

    val closeDialog: LiveData<Boolean> get() = _closeDialog
    private var _closeDialog = MutableLiveData<Boolean>()

    private var selectedMood = -1

    fun setCurrentMood(selectedMood: Int) {
        this.selectedMood = selectedMood
        when (selectedMood) {
            0 -> _selectedMoodText.value = MoodSelectorDialog.HAPPY_MOOD_TEXT
            1 -> _selectedMoodText.value = MoodSelectorDialog.NEUTRAL_MOOD_TEXT
            2 -> _selectedMoodText.value = MoodSelectorDialog.SAD_MOOD_TEXT
            else -> {
                _selectedMoodText.value = MoodSelectorDialog.SAD_MOOD_TEXT
            }
        }
    }

    fun setCommentVisibility() {
        _edtCommentVisibility.value = !(edtCommentVisibility.value ?: false)
    }

    fun hasAnyMoodBeenSelected(): Boolean {
        return selectedMood != -1
    }

    fun addMoodToDatabase(mood: Mood) {
        viewModelScope.launch {
            val added = repository.moodDao.insert(mood)
            if (added > 0) _closeDialog.value = true
        }
    }

    fun getSelectedMood(): Int {
        return selectedMood
    }

}