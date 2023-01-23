package com.example.restaurantsapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Reserve::class), version = 1)
abstract class ReserveDatabase : RoomDatabase(){
    abstract fun reserveDao(): ReserveDao
}
