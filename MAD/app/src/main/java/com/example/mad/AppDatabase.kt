package com.example.mad

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HowManyFingersResult::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun howManyFingersDao(): HowManyFingersDao
}
