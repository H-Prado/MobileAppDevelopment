package com.example.restaurantsapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.dialog_reserve_hour.*

class ReserveCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_create)
        val restaurantUbication = intent.getStringExtra("restaurantUbicationToPass")
        Log.i("Value Passed", restaurantUbication!!)
    }

    fun runConfirmDate(view: View){
        val datePicker = findViewById<DatePicker>(R.id.reserve_date)
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        Log.i("Choosen date", day.toString() + "/" + (month+1).toString() + "/" + year.toString())

        val dialog = Dialog(this)
        dialog.setTitle("Reserve Dialog")
        dialog.setContentView(R.layout.dialog_reserve_hour)
        dialog.btn_reserve_hour_confirm.setOnClickListener{
            if(dialog.reserve_hour.hour > 22 || dialog.reserve_hour.hour < 9 || (dialog.reserve_hour.hour == 22 && dialog.reserve_hour.minute != 0))
                Log.e("Hour Error", dialog.reserve_hour.hour.toString() + ":" + dialog.reserve_hour.minute + " is not a valid hour")
            else{
                Log.i("Reservation Confirmed", "Reservation at " + dialog.reserve_hour.hour + ":" + dialog.reserve_hour.minute + " was confirmed!")
                dialog.dismiss()
            }
        }
        dialog.btn_reserve_hour_cancel.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }
}