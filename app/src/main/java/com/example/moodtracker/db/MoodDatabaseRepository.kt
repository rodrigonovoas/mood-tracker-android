package com.example.moodtracker.db

import android.content.Context
import com.example.moodtracker.data.dao.MoodDao
import com.example.moodtracker.data.entity.Mood

class MoodDatabaseRepository(context: Context) {
    var moodDao: MoodDao = MoodDatabase.getInstance(context).moodDao()

    suspend fun getAllMood(): List<Mood> {
        return moodDao.getAll()
    }


    suspend fun insertMood(book: Mood): Long {
        return moodDao.insert(book)
    }


    suspend fun deleteMood(book: Mood) {
        moodDao.delete(book)
    }
}