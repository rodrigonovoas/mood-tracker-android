package com.example.moodtracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moodtracker.data.dao.MoodDao
import com.example.moodtracker.data.entity.Mood

@Database(entities = [Mood::class], version = 1)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    companion object {
        private var instance: MoodDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MoodDatabase {
            return instance ?:
                createDatabase(ctx)
        }

        fun createDatabase(ctx: Context): MoodDatabase {
            instance = Room.databaseBuilder(ctx.applicationContext, MoodDatabase::class.java,
                "mood_database")
                .fallbackToDestructiveMigration()
                .build()
            return instance!!
        }
    }

}