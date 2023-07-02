package com.example.moodtracker.ui.moodTracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.FakeMoodDatabaseRepository
import com.example.moodtracker.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoodTrackerViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: MoodTrackerViewModel

    @Before
    fun setup() {
        viewModel = MoodTrackerViewModel(FakeMoodDatabaseRepository())
    }

    @Test
    fun shouldGetMoodsWhenRepositoryCalled() {
        val expected = listOf<Mood>()
        viewModel.getMoods()

        val value = viewModel.moods.getOrAwaitValue()
        assertEquals(expected, value)
    }

    @Test
    fun shouldSetMoodWhenSelected() {
        val moodExample = Mood(1,1688319988, 0, "testing")
        viewModel.setSelectedMood(moodExample)

        val value = viewModel.selectedMood.getOrAwaitValue()
        assertEquals(moodExample, value)
    }

    @Test
    fun shouldAddMoodButtonBeVisibleWhenSetAsVisible() {
        viewModel.setAddMoodVisibility(true)
        val value = viewModel.btnAddMoodVisibility.getOrAwaitValue()
        assertEquals(true, value)
    }

    @Test
    fun shouldAddMoodButtonBeInvisibleWhenSetAsInvisible() {
        viewModel.setAddMoodVisibility(false)
        val value = viewModel.btnAddMoodVisibility.getOrAwaitValue()
        assertEquals(false, value)
    }

    @Test
    fun shouldGetMoodStatisticsWhenCalledFromRepository() {
        viewModel.getMoods()

        val happyValue = viewModel.getHappyMoodQuantity()
        val neutralValue = viewModel.getNeutralMoodQuantity()
        val sadValue = viewModel.getSadMoodQuantity()

        assertEquals("2", happyValue)
        assertEquals("3", neutralValue)
        assertEquals("4", sadValue)
    }
}