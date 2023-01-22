package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
    }

    fun checkResult(view: View) {
        val resultText: TextView = findViewById(R.id.result_text)
        val resultImage: ImageView = findViewById(R.id.result_image)
        val weightText: EditText = findViewById(R.id.user_weight)
        val heightText: EditText = findViewById(R.id.user_height)

        try {
            val userWeight= weightText.text.toString().toFloat()
            val userHeight= heightText.text.toString().toFloat()/100
            val BMI = userWeight / (userHeight * userHeight)

            if (BMI < 18.5 ) {
                resultText.text = "You are underweight!"
                resultImage.setImageResource(R.drawable.underweight)
            }
            else if(BMI >= 18.5 && BMI <= 24.9){
                resultText.text = "You are healthy!"
                resultImage.setImageResource(R.drawable.healthy)
            }
            else if(BMI >= 25 && BMI <= 29.9){
                resultText.text = "You are overWeight!"
                resultImage.setImageResource(R.drawable.overweight)
            }
            else{
                resultText.text = "You have obesity! You must take care!"
                resultImage.setImageResource(R.drawable.obesity)
            }

            Log.i("BMI Activity","The BMI of this weight and height is: " + BMI)

        }catch (e: NumberFormatException) {
            Log.i("BMI Activity ERROR", "The user must introduce his weight and height")
            resultText.text = "You need to introduce your weight and height!"
        }
    }

    fun returnToMenu(view: View) {
        var intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}