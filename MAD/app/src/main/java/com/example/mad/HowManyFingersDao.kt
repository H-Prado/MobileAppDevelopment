package com.example.mad

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HowManyFingersDao {
    @Query("SELECT * FROM howManyFingersResult")
    fun getAll(): List<HowManyFingersResult>

    @Query("SELECT * FROM howManyFingersResult WHERE gameId IN (:gameIds)")
    fun loadAllByIds(gameIds: IntArray): List<HowManyFingersResult>

    @Insert
    fun insertAll(vararg users: HowManyFingersResult)

    @Delete
    fun delete(user: HowManyFingersResult)
}