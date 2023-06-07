package com.example.moodtracker.db

import com.example.moodtracker.data.entity.Mood

class FakeMoodDatabaseRepository: MoodRepository {
    override suspend fun getAllMood(): List<Mood> {
        return listOf()
    }

    override suspend fun insertMood(book: Mood): Long {
        return 1
    }

    override suspend fun deleteMood(book: Mood) { }
}