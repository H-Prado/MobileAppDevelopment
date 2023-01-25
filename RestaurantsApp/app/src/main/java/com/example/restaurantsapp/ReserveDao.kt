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

    @Query("SELECT * FROM reserve Where phone IN (:phone)")
    fun getReservesByPhone(phone: String): List<Reserve>

    @Query("DELETE FROM reserve WHERE reserveId = :reserveId")
    fun deleteByReserveId(reserveId: Int)

    @Insert
    fun insertAll(vararg users: Reserve)

    @Delete
    fun delete(user: Reserve)
}