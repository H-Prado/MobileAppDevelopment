package com.example.restaurantsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun runOwnerMenu(view: View) {
        spWriteTo("owner")
        var intent = Intent(this, OwnerMenuActivity::class.java)
        startActivity(intent)
    }

    fun runClientMenu(view: View) {
        spWriteTo("client")
        var intent = Intent(this, ClientMenuActivity::class.java)
        startActivity(intent)
    }
    fun runAdmin(view: View) {
        spWriteTo("admin")
        var intent = Intent(this, BarListActivity::class.java)
        startActivity(intent)
    }

    fun spWriteTo(role: String){
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("user_role", role)
        editor.apply()
    }
}