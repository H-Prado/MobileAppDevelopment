package com.example.restaurantsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurant ORDER BY name")
    fun getAll(): List<Restaurant>

    @Query("SELECT * FROM restaurant WHERE ubication IN (:ubications)")
    fun loadAllByUbications(ubications: IntArray): List<Restaurant>

    @Query("SELECT * FROM restaurant WHERE owner IN (:owner)")
    fun getRestaurantsByOwner(owner: String): List<Restaurant>

    @Query("SELECT * FROM restaurant WHERE ubication IN (:ubication)")
    fun getRestaurantByUbication(ubication: String): Restaurant

    @Query("DELETE FROM restaurant WHERE ubication = :ubication")
    fun deleteByUbication(ubication: String)

    @Query("DELETE from restaurant")
    fun deleteAll()

    @Insert
    fun insertAll(vararg users: Restaurant)

    @Delete
    fun delete(user: Restaurant)
}