package com.example.barapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.dialog_register_login.*
import kotlinx.android.synthetic.main.dialog_register_login.input_password
import kotlinx.android.synthetic.main.toast_error_register_login.view.*

class OwnerMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_menu)

        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userRole = sp.getString("user_role", "")
        Log.i("User information: ", "Role:" + userRole!!)
    }

    fun runLogIn(view: View) {
        val dialog = Dialog(this)
        dialog.setTitle("Log In")
        dialog.setContentView(R.layout.dialog_register_login)
        dialog.btn_confirm.text = "Log In"
        dialog.btn_confirm.setOnClickListener {
            if (dialog.input_username.text.isNullOrBlank()) {
                Log.e("Register Error", "Username field is empty")
                showToastErrorMessage("Username field can't be empty!")
            }else if(dialog.input_password.text.isNullOrBlank()) {
                Log.e("Register Error", "Password field is empty")
                showToastErrorMessage("Password field can't be empty!")
            }else {
                try {
                    val db = Room.databaseBuilder(
                        applicationContext,
                        UserDatabase::class.java,
                        "userDatabase"
                    )
                        .allowMainThreadQueries().enableMultiInstanceInvalidation()
                        .fallbackToDestructiveMigration().build()
                    val userDao = db.userDao()

                    val dbUser = userDao.getUserByUsername(dialog.input_username.text.toString())
                    if(dbUser == null){
                        Log.e("Log In Error", "User does not exist")
                        showToastErrorMessage("User name not found!")
                    }else if(dbUser.password.equals(dialog.input_password.text.toString())){
                        Log.i("Succesfully logged in", "User name and password were right")
                        var intent = Intent(this, BarListActivity::class.java)
                        startActivity(intent)
                    }else{
                        Log.e("Log In Error", "Incorrect Password")
                        showToastErrorMessage("Incorrect Password!")
                    }
                } catch (e: Exception) {
                    Log.e("Log In Error", e.toString())
                }
            }
        }
        dialog.show()
    }

    fun runRegister(view: View) {
        val dialog = Dialog(this)
        dialog.setTitle("Register")
        dialog.setContentView(R.layout.dialog_register_login)
        dialog.btn_confirm.text = "Register"
        dialog.btn_confirm.setOnClickListener {
            if (dialog.input_username.text.isNullOrBlank()) {
                Log.e("Register Error", "Username field is empty")
                showToastErrorMessage("Username field can't be empty!")
            }else if(dialog.input_password.text.isNullOrBlank()) {
                Log.e("Register Error", "Password field is empty")
                showToastErrorMessage("Password field can't be empty!")
            }else{
                try {
                    val db = Room.databaseBuilder(
                        applicationContext,
                        UserDatabase::class.java,
                        "userDatabase"
                    )
                        .allowMainThreadQueries().enableMultiInstanceInvalidation()
                        .fallbackToDestructiveMigration().build()
                    val userDao = db.userDao()
                    val newUser = User(
                        userName = dialog.input_username.text.toString().lowercase(),
                        password = dialog.input_password.text.toString()
                    )
                    userDao.insertAll(newUser)
                    Log.i("Succesfully registered", "Register went through. New username: " + dialog.input_username.text.toString().lowercase() +
                          "New Password: " + dialog.input_password.text.toString())

                    var intent = Intent(this, BarListActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("Log In Error", e.toString())
                    showToastErrorMessage("User already exists! Try another one")
                }
            }
        }
        dialog.show()
    }

    fun showToastErrorMessage(message: String) {
        var toastView = layoutInflater.inflate(R.layout.toast_error_register_login,null)
        var toast = Toast.makeText(this,"This is the toast message", Toast.LENGTH_LONG)
        toast.view = toastView
        toastView.toast_register_login_error_message.text = message
        toast.show()
    }

    fun spWriteTo(action: String){
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("user_action", action)
        editor.apply()
    }
}