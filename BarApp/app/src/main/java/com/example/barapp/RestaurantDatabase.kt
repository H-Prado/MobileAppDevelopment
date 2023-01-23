package com.example.barapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Restaurant::class), version = 1)
abstract class RestaurantDatabase : RoomDatabase(){
    abstract fun restaurantDao(): RestaurantDao
}
