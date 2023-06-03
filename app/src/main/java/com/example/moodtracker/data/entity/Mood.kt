package com.example.moodtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mood(
    @PrimaryKey(autoGenerate = true ) val id: Int,
    val creationDate: Long,
    val moodType: Int,
    val comment: String?
)