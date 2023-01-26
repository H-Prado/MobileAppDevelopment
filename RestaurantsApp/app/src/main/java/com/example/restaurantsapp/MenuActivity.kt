package com.example.restaurantsapp

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
        var intent = Intent(this, OwnerMenuActivity::class.java)
        startActivity(intent)
    }

    fun runClientMenu(view: View) {
        var intent = Intent(this, ClientMenuActivity::class.java)
        startActivity(intent)
    }
    fun runAdmin(view: View) {
        var intent = Intent(this, AdminMenuActivity::class.java)
        startActivity(intent)
    }
}