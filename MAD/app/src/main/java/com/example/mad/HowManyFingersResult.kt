package com.example.mad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class HowManyFingersResult(
    @PrimaryKey(autoGenerate = true) val gameId: Int,
    @ColumnInfo(name = "correct_number") val correctNumber: String?,
    @ColumnInfo(name = "user_number") val userNumber: String?,
    @ColumnInfo(name = "result") val result: String?
    ){
    @Ignore constructor(correctNumber: String?, userNumber: String?, result: String?):
            this(0, correctNumber,userNumber, result)
    }


