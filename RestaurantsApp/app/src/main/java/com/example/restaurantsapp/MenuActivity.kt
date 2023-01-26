package com.example.restaurantsapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_admin_credentials.*
import kotlinx.android.synthetic.main.dialog_interact.*
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*

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
        val dialog = Dialog(this)
        dialog.setTitle("Admin Credentials Dialog")
        dialog.setContentView(R.layout.dialog_admin_credentials)
        dialog.btn_admin_credentials_confirm.setOnClickListener{
            if(dialog.input_admin_username.text.toString().lowercase().equals("admin") && dialog.input_admin_password.text.toString().equals("admin")) {
                dialog.dismiss()

                var intent = Intent(this, AdminMenuActivity::class.java)
                startActivity(intent)
            }else{
                showToastMessage("error", "Invalid username/password. Try Again")
                Log.e("Admin Credentials Error", "Invalid username or invalid password")
            }
        }
        dialog.btn_admin_credentials_cancel.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showToastMessage(status: String, message: String) {
        if(status.equals("success")){
            var toastView = layoutInflater.inflate(R.layout.toast_success,null)
            var toast = Toast.makeText(this,"This is the toast message", Toast.LENGTH_LONG)
            toast.view = toastView
            toastView.toast_success_message.text = message
            toast.show()
        }
        else if (status.equals("error")){
            var toastView = layoutInflater.inflate(R.layout.toast_error,null)
            var toast = Toast.makeText(this,"This is the toast message", Toast.LENGTH_LONG)
            toast.view = toastView
            toastView.toast_error_message.text = message
            toast.show()
        }
    }
}