package com.example.moodtracker.ui.moodSelector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoodSelectorViewModel: ViewModel() {

    val selectedMoodText: LiveData<String> get() = _selectedMoodText
    private var _selectedMoodText = MutableLiveData<String>()

    val edtCommentVisibility: LiveData<Boolean> get() = _edtCommentVisibility
    private var _edtCommentVisibility = MutableLiveData<Boolean>()

    fun setCurrentMood(selectedMood: Int) {
        when (selectedMood) {
            0 -> _selectedMoodText.value = "Happy"
            1 -> _selectedMoodText.value = "Neutral"
            2 -> _selectedMoodText.value = "Sad"
            else -> {
                _selectedMoodText.value = "Neutral"
            }
        }
    }

    fun setCommentVisibility() {
        _edtCommentVisibility.value = !(edtCommentVisibility.value ?: false)
    }

    fun hasAnyMoodBeenSelected(): Boolean {
        return selectedMoodText.value?.isNotEmpty() ?: false
    }

}