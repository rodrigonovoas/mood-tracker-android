package com.example.moodtracker.data.dao

import androidx.room.*
import com.example.moodtracker.data.entity.Mood

@Dao
interface MoodDao {
    @Query("SELECT * FROM Mood")
    suspend fun getAll(): List<Mood>

    @Insert
    suspend fun insert(mood: Mood): Long

    @Update
    suspend fun update(mood: Mood): Int

    @Delete
    suspend fun delete(mood: Mood): Int
}