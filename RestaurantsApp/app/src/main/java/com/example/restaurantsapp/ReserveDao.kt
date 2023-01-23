package com.example.restaurantsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReserveDao {
    @Query("SELECT * FROM reserve")
    fun getAll(): List<Reserve>

    @Query("SELECT * FROM reserve WHERE phone IN (:phones)")
    fun loadAllReservesByPhone(phones: IntArray): List<Reserve>

    @Query("SELECT * FROM reserve Where date IN (:date) AND ubication IN (:ubication)")
    fun getReservesByRestaurantAndDate(ubication: String, date: String): List<Reserve>

    @Insert
    fun insertAll(vararg users: Reserve)

    @Delete
    fun delete(user: Reserve)
}