package com.example.restaurantsapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "password") val password: String?
)

