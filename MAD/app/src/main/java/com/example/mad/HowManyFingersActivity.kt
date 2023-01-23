package com.example.mad

import HowManyFingersResultsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_how_many_fingers.*
import kotlin.random.Random


class HowManyFingersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_many_fingers)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "howManyFingersresultDatabase")
            .allowMainThreadQueries().enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build()
        val howManyFingersDao = db.howManyFingersDao()

        var listOfResults: List<HowManyFingersResult> = howManyFingersDao.getAll()

        var resultsAdapter = HowManyFingersResultsAdapter(this, listOfResults)
        how_many_fingers_list.adapter = resultsAdapter
    }

    fun checkResult(view: View) {
        val correctNumber = Random.nextInt(1,11)
        val resultText: TextView = findViewById(R.id.result_text)
        val userGuessedNumber: EditText = findViewById(R.id.user_guessed_number)

        try {
            Log.i("How Many Fingers","This iteration the correct number was: " + correctNumber)
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "howManyFingersresultDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build()
            val howManyFingersDao = db.howManyFingersDao()

            if (correctNumber == userGuessedNumber.text.toString().toInt()) {
                resultText.text = "You were right! The correct number was " + correctNumber
                val howManyFingersResult = HowManyFingersResult(correctNumber = correctNumber.toString(), userNumber = userGuessedNumber.text.toString(), result = "W")
                howManyFingersDao.insertAll(howManyFingersResult)

            } else {
                resultText.text = "You were wrong! The correct number was " + correctNumber
                val howManyFingersResult = HowManyFingersResult(correctNumber = correctNumber.toString(), userNumber = userGuessedNumber.text.toString(), result = "L")
                howManyFingersDao.insertAll(howManyFingersResult)
            }

            var listOfResults: List<HowManyFingersResult> = howManyFingersDao.getAll()
            var resultsAdapter = HowManyFingersResultsAdapter(this, listOfResults)
            how_many_fingers_list.adapter = resultsAdapter
        }catch (e: NumberFormatException) {
            Log.i("How Many Fingers", "The user didn't try to guess the number")
            resultText.text = "You need to guess a number!"
        }
    }

    fun returnToMenu(view: View) {
        var intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}