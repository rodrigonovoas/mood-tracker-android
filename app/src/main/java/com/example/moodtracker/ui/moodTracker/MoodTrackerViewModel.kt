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

    fun getMoods() {
        viewModelScope.launch {
            _moods.value = repository.getAllMood()
        }
    }
}