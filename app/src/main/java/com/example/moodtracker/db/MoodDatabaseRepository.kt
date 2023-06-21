package com.example.moodtracker.db

import android.content.Context
import com.example.moodtracker.data.dao.MoodDao
import com.example.moodtracker.data.entity.Mood

class MoodDatabaseRepository(context: Context): MoodRepository {
    private var moodDao: MoodDao = MoodDatabase.getInstance(context).moodDao()

    override suspend fun getAllMood(): List<Mood> {
        return moodDao.getAll()
    }

    override suspend fun getHappyMoodQuantity(): Int {
        return moodDao.getHappyMoodQuantity()
    }

    override suspend fun getNeutralMoodQuantity(): Int {
        return moodDao.getNeutralMoodQuantity()
    }

    override suspend fun getSadMoodQuantity(): Int {
        return moodDao.getSadMoodQuantity()
    }
    override  suspend fun insertMood(mood: Mood): Long {
        return moodDao.insert(mood)
    }

    override suspend fun updateMood(mood: Mood): Int {
        return moodDao.update(mood)
    }


    override  suspend fun deleteMood(mood: Mood) {
        moodDao.delete(mood)
    }
}