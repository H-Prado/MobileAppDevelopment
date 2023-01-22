package com.example.mad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_get_value.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        try{
           user_value_to_be_passed.setText(intent.getStringExtra("valueToBePassedBack"))
        }catch(e: Exception){ Log.e("Exception: ", e.toString())}
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
        var intent = Intent(this, GetValueActivity::class.java)
        intent.putExtra("valueToBePassed", user_value_to_be_passed.text.toString())
        startActivity(intent)
    }
    fun spWriteTo(view: View){
        val sp = getPreferences(Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("etext", sp_value.text.toString())
        editor.apply()
    }
    fun spReadFrom(view: View){
        val sp = getPreferences(Context.MODE_PRIVATE)
        sp_value.setText(sp.getString("etext",""))
    }
}