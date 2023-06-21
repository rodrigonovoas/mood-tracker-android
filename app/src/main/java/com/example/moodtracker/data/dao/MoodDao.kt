package com.example.moodtracker.data.dao

import androidx.room.*
import com.example.moodtracker.data.entity.Mood

@Dao
interface MoodDao {
    @Query("SELECT * FROM Mood")
    suspend fun getAll(): List<Mood>

    @Query("SELECT count(id) FROM Mood where moodType = 0")
    suspend fun getHappyMoodQuantity(): Int

    @Query("SELECT count(id) FROM Mood where moodType = 1")
    suspend fun getNeutralMoodQuantity(): Int

    @Query("SELECT count(id) FROM Mood where moodType = 2")
    suspend fun getSadMoodQuantity(): Int

    @Insert
    suspend fun insert(mood: Mood): Long

    @Update
    suspend fun update(mood: Mood): Int

    @Delete
    suspend fun delete(mood: Mood): Int
}