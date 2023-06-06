package com.example.moodtracker.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moodtracker.data.dao.MoodDao
import com.example.moodtracker.data.entity.Mood
import com.example.moodtracker.utils.DateUtils
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoodDatabaseTest : TestCase() {

    private lateinit var db: MoodDatabase
    private lateinit var dao: MoodDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MoodDatabase::class.java).build()
        dao = db.moodDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun whenMultipleMoodAddedShouldDatabaseRetrieveThem() = runBlocking {
        val mood_1 = Mood(1,DateUtils.getCurrentDateTimeAsTimeStamp(),
            0,null)
        dao.insert(mood_1)

        val mood_2 = Mood(2,DateUtils.getCurrentDateTimeAsTimeStamp(),
            1,null)
        dao.insert(mood_2)

        val mood_3 = Mood(3,DateUtils.getCurrentDateTimeAsTimeStamp(),
            2,null)
        dao.insert(mood_3)

        val moods = dao.getAll()

        var moodsRetrieved = moods.contains(mood_1)
                && moods.contains(mood_2)
                && moods.contains(mood_3)

        assertThat(moodsRetrieved).isTrue()
    }

    @Test
    fun whenMoodDeletedShouldDatabaseNotRetrieveIt() = runBlocking {
        val mood = Mood(1,DateUtils.getCurrentDateTimeAsTimeStamp(),
            0,null)
        dao.insert(mood)

        dao.delete(mood)

        val moods = dao.getAll()

        assertThat(moods.contains(mood)).isFalse()
    }
}