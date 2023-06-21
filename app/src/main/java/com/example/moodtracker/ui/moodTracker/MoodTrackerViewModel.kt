package com.example.moodtracker.ui.moodTracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.MoodDatabaseRepository
import kotlinx.coroutines.launch

class MoodTrackerViewModel(val repository: MoodDatabaseRepository): ViewModel() {
    private val _moods = MutableLiveData<List<Mood>>()
    val moods: LiveData<List<Mood>> get() = _moods

    private val _selectedMood = MutableLiveData<Mood>()
    val selectedMood: LiveData<Mood> get() = _selectedMood

    private val _btnAddMoodVisibility = MutableLiveData<Boolean>()
    val btnAddMoodVisibility: LiveData<Boolean> get() = _btnAddMoodVisibility

    private var totalHappyMood: Int = 0
    private var totalNeutralMood: Int = 0
    private var totalSadMood: Int = 0

    fun getMoods() {
        getTotalMood()
        viewModelScope.launch {
            _moods.value = repository.getAllMood()
        }
    }

    fun setSelectedMood(mood: Mood) {
        _selectedMood.value = mood
    }

    fun setAddMoodVisibility(visible: Boolean) {
        _btnAddMoodVisibility.value = visible
    }

    private fun getTotalMood() {
        viewModelScope.launch {
            totalHappyMood = repository.getHappyMoodQuantity()
            totalNeutralMood = repository.getNeutralMoodQuantity()
            totalSadMood = repository.getSadMoodQuantity()
        }
    }

    fun getHappyMoodQuantity(): String {
        return totalHappyMood.toString()
    }

    fun getNeutralMoodQuantity(): String {
        return totalNeutralMood.toString()
    }

    fun getSadMoodQuantity(): String {
        return totalSadMood.toString()
    }
}