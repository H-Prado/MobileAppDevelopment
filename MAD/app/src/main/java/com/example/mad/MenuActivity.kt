package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun runTestActionsActivity(view: View) {
        var intent = Intent(this, TestActionsActivity::class.java)
        startActivity(intent)
    }

    fun runHowManyFingers(view: View) {
        var intent = Intent(this, HowManyFingersActivity::class.java)
        startActivity(intent)
    }

    fun runBMI(view: View) {
        var intent = Intent(this, BMIActivity::class.java)
        startActivity(intent)
    }

    fun runFlowerList(view: View) {
        var intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
    fun runActivityAndPassValue(view: View) {
        var intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}