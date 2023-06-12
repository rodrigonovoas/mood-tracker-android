package com.example.moodtracker.db

import com.example.moodtracker.data.entity.Mood

interface MoodRepository {
    suspend fun getAllMood(): List<Mood>
    suspend fun insertMood(book: Mood): Long

    suspend fun updateMood(mood: Mood): Int
    suspend fun deleteMood(book: Mood)
}