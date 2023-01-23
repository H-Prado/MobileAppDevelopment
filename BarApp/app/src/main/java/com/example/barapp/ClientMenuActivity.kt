package com.example.barapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class ClientMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_menu)
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userRole = sp.getString("user_role", "")
        Log.i("User information: ", "Role:" + userRole!!)
    }

    fun runReservation(view: View) {
        spWriteTo("reserve")
        var intent = Intent(this, BarListActivity::class.java)
        startActivity(intent)
    }
    fun runCancelation(view: View) {
        spWriteTo("cancel")
        var intent = Intent(this, BarListActivity::class.java)
        startActivity(intent)
    }

    fun spWriteTo(action: String){
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("user_action", action)
        editor.apply()
    }
}