package com.example.mad

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class DatabaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "howManyFingersresultDatabase")
                 .allowMainThreadQueries().enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build()
        val howManyFingersDao = db.howManyFingersDao()

//        val howManyFingersResult = HowManyFingersResult(userNumber = 1, correctNumber = 2, win = false)
//        howManyFingersDao.insertAll(howManyFingersResult)
//
//        val howManyFingersResults: List<HowManyFingersResult> = howManyFingersDao.getAll()
//        for(howManyFingersResult in howManyFingersResults){
//            Log.i("How Many Fingers Database", howManyFingersResult.toString())
//        }
    }
}