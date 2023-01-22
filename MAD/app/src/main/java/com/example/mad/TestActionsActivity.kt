package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_actions.*

class TestActionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_actions)
    }

    fun writeToLogCat(view: View) {
        Log.i("MyAPP","Message from my app to the logs")
    }

    fun showToastMessage(view: View) {
        var toastView = layoutInflater.inflate(R.layout.custom_toast_layout,null)
        var toast = Toast.makeText(this,"This is the toast message", Toast.LENGTH_LONG)
        toast.view = toastView
        toast.show()
    }

    fun writeToEditText(view: View) {
        write_to_edit_text.setText("Yeah! Something is here now! Well done =)")
    }

    fun copyUserValueToEditText(view: View) {
        user_value_write_to_edit_text.setText(user_value_edit_text.getText().toString())
    }

    fun returnToMenu(view: View) {
        var intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}