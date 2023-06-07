package com.example.moodtracker.ui.moodSelector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.db.FakeMoodDatabaseRepository
import com.example.moodtracker.getOrAwaitValue
import com.example.moodtracker.utils.DateUtils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MoodSelectorDialogTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: MoodSelectorViewModel

    @Before
    fun setup() {
        viewModel = MoodSelectorViewModel(FakeMoodDatabaseRepository())
    }

    @Test
    fun shouldSetHappyMoodInTextviewWhenMoodSelected() {
        val expectedText = MoodSelectorDialog.HAPPY_MOOD_TEXT

        viewModel.setCurrentMood(MoodSelectorDialog.HAPPY_MOOD)

        val value = viewModel.selectedMoodText.getOrAwaitValue()

        assertEquals(expectedText, value)
    }

    @Test
    fun shouldSetNeutralMoodInTextviewWhenMoodSelected() {
        val expectedText = MoodSelectorDialog.NEUTRAL_MOOD_TEXT

        viewModel.setCurrentMood(MoodSelectorDialog.NEUTRAL_MOOD)

        val value = viewModel.selectedMoodText.getOrAwaitValue()

        assertEquals(expectedText, value)
    }

    @Test
    fun shouldSetSadMoodInTextviewWhenMoodSelected() {
        val expectedText = MoodSelectorDialog.SAD_MOOD_TEXT

        viewModel.setCurrentMood(MoodSelectorDialog.SAD_MOOD)

        val value = viewModel.selectedMoodText.getOrAwaitValue()

        assertEquals(expectedText, value)
    }

    @Test
    fun shouldCommentEditTextBeVisibleWhenCommentSectionTapped() {
        val expectedValue = true
        viewModel.setCommentVisibility()
        val visibility = viewModel.edtCommentVisibility.getOrAwaitValue()
        assertEquals(expectedValue, visibility)
    }

    @Test
    fun shouldCommentEditTextBeInvisibleWhenCommentSectionTappedTwice() {
        val expectedValue = false
        viewModel.setCommentVisibility()
        viewModel.setCommentVisibility()
        val visibility = viewModel.edtCommentVisibility.getOrAwaitValue()

        assertEquals(expectedValue, visibility)
    }

   @Test
    fun shouldNotContinueWhenMoodIsNotSelected() {
       val expectedValue = false
       assertEquals(expectedValue, viewModel.hasAnyMoodBeenSelected())
    }

    @Test
    fun shouldContinueWhenMoodIsSelected() {
        val expectedValue = true
        viewModel.setCurrentMood(MoodSelectorDialog.HAPPY_MOOD)
        assertEquals(expectedValue, viewModel.hasAnyMoodBeenSelected())
    }

    @Test
    fun shouldAddSelectedMoodAfterContinue() {
        val expectedValue = true
        viewModel.setCurrentMood(MoodSelectorDialog.HAPPY_MOOD)
        viewModel.addMoodToDatabase(Mood(null,DateUtils.getCurrentDateTimeAsTimeStamp(), viewModel.getSelectedMood(), ""))
        val moodAdded = viewModel.closeDialog.getOrAwaitValue()
        assertEquals(expectedValue, moodAdded)
    }
}