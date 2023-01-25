package com.example.restaurantsapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Reserve(
    @PrimaryKey(autoGenerate = true) val reserveId: Int,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "hour") val hour: String,
    @ColumnInfo(name = "ubication") val ubication: String,
    @ColumnInfo(name = "assistants") val assistants: String
){
    @Ignore
    constructor(phone: String, date: String, hour: String, ubication: String, assistants: String):
        this(0, phone, date, hour, ubication, assistants)
}

