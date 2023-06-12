package com.example.moodtracker.ui.moodComment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.MoodDatabaseRepository
import kotlinx.coroutines.launch

class MoodCommentViewModel(val repository: MoodDatabaseRepository): ViewModel() {

    val moodUpdated: LiveData<Boolean> get() = _moodUpdated
    private var _moodUpdated = MutableLiveData<Boolean>()

    fun updateCommentary(oldMood: Mood, comment: String) {
        val newMood = Mood(oldMood.id, oldMood.creationDate, oldMood.moodType, comment)
        viewModelScope.launch {
            val updated = repository.updateMood(newMood)
            if(updated == 1) { _moodUpdated.value = true }
        }
    }

}