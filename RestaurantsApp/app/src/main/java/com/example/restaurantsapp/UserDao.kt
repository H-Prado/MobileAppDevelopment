package com.example.restaurantsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE username IN (:usernames)")
    fun loadAllByUsernames(usernames: IntArray): List<User>

    @Query("SELECT * from user WHERE username = :username")
    fun getUserByUsername(username: String): User

    @Query("DELETE from user")
    fun deleteAll()

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}