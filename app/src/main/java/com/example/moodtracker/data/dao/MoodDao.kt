package com.example.moodtracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodtracker.data.entity.Mood

@Dao
interface MoodDao {
    @Query("SELECT * FROM Mood")
    suspend fun getAll(): List<Mood>

    @Insert
    suspend fun insert(mood: Mood): Long

    @Delete
    suspend fun delete(mood: Mood): Int
}