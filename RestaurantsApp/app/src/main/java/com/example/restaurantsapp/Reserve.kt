package com.example.restaurantsapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Reserve(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "hour") val hour: String,
    @ColumnInfo(name = "ubication") val ubication: String,
    @ColumnInfo(name = "assistants") val assistants: String
)

