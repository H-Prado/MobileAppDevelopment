package com.example.barapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurant")
    fun getAll(): List<Restaurant>

    @Query("SELECT * FROM restaurant WHERE ubication IN (:ubications)")
    fun loadAllByUbications(ubications: IntArray): List<Restaurant>

    @Insert
    fun insertAll(vararg users: Restaurant)

    @Delete
    fun delete(user: Restaurant)
}