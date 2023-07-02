package com.example.moodtracker.ui.moodComment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.FakeMoodDatabaseRepository
import com.example.moodtracker.getOrAwaitValue
import com.example.moodtracker.ui.moodTracker.MoodTrackerViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoodCommentViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoodCommentViewModel

    @Before
    fun setup() {
        viewModel = MoodCommentViewModel(FakeMoodDatabaseRepository())
    }

    @Test
    fun shouldUpdateCommentAfterRepositoryCall() {
        val oldMood = Mood(1,1688319988, 0, "testing")
        val newComment = "testing 2"
        viewModel.updateCommentary(oldMood, newComment)

        val value = viewModel.moodUpdated.getOrAwaitValue()
        assertEquals(true, value)
    }
}