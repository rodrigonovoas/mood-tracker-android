package com.example.moodtracker.db

import com.example.moodtracker.data.entity.Mood

class FakeMoodDatabaseRepository: MoodRepository {
    override suspend fun getAllMood(): List<Mood> {
        return listOf()
    }

    override suspend fun getHappyMoodQuantity(): Int {
        return 2
    }

    override suspend fun getNeutralMoodQuantity(): Int {
        return 3
    }

    override suspend fun getSadMoodQuantity(): Int {
        return 4
    }

    override suspend fun insertMood(book: Mood): Long {
        return 1
    }

    override suspend fun updateMood(mood: Mood): Int {
       return 0
    }

    override suspend fun deleteMood(book: Mood) { }
}