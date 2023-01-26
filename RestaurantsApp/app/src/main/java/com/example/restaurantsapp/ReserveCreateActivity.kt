package com.example.restaurantsapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.dialog_reserve_hour.*
import kotlinx.android.synthetic.main.dialog_reserve_hour.reserve_assistants
import kotlinx.android.synthetic.main.dialog_reserve_hour.reserve_hour
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*
import java.util.*

class ReserveCreateActivity : AppCompatActivity() {

    var restaurantUbication: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_create)
        restaurantUbication = intent.getStringExtra("restaurantUbicationToPass")!!
        Log.i("Value Passed", restaurantUbication!!)
    }

    fun runConfirmDate(view: View){
        var month = ""
        var correctNumber = false
        val datePicker = findViewById<DatePicker>(R.id.reserve_date)
        if(datePicker.month + 1 < 10){
            month = "0" + (datePicker.month + 1)
        }else{
            month = (datePicker.month + 1).toString()
        }

        if(datePicker.year < Calendar.getInstance().get(Calendar.YEAR)
            || datePicker.year == Calendar.getInstance().get(Calendar.YEAR) && datePicker.month < Calendar.getInstance().get(Calendar.MONTH)
            || datePicker.year == Calendar.getInstance().get(Calendar.YEAR) && datePicker.month == Calendar.getInstance().get(Calendar.MONTH) && datePicker.dayOfMonth < Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        {
            Log.e("Reserve Error", "Reserve must be for today or later!")
            showToastMessage("error", "Reserve must be for today or later!")
        }

        else{
            val date = datePicker.dayOfMonth.toString() + "/" + month + "/" + datePicker.year
            Log.i("Choosen date", date)

            val dialog = Dialog(this)
            dialog.setTitle("Reserve Dialog")
            dialog.setContentView(R.layout.dialog_reserve_hour)
            dialog.reserve_hour.setIs24HourView(true)
            dialog.btn_reserve_hour_confirm.setOnClickListener{
                try{
                    correctNumber = false
                    dialog.reserve_phone.text.toString().toInt()
                    correctNumber = true
                    dialog.reserve_assistants.text.toString().toInt()
                    if (dialog.reserve_assistants.text.toString().toInt() < 1){
                        throw NumberFormatException()
                    }

                    var today = false
                    if(datePicker.year == Calendar.getInstance().get(Calendar.YEAR) && datePicker.month == Calendar.getInstance().get(Calendar.MONTH) && datePicker.dayOfMonth == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                        today = true
                    }
                    var hour =""
                    if (dialog.reserve_hour.minute.toString().length == 1){
                        hour = dialog.reserve_hour.hour.toString() + ":0" + dialog.reserve_hour.minute
                    }else {
                        hour = dialog.reserve_hour.hour.toString() + ":" + dialog.reserve_hour.minute
                    }
                    if(dialog.reserve_hour.hour > 22 || dialog.reserve_hour.hour < 12 || (dialog.reserve_hour.hour == 22 && dialog.reserve_hour.minute != 0)) {
                        Log.e("Hour Error", hour + " is not a valid hour")
                        showToastMessage("error", hour + " is not a valid hour")
                    }else if(dialog.reserve_phone.text.toString().length != 9){
                        Log.e("Reserve Error", "Phone number doesn't have 9 numbers!")
                        showToastMessage("error", "Phone number doesn't have 9 numbers!")
                    }else if(today && dialog.reserve_hour.hour < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                            || today && dialog.reserve_hour.hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && dialog.reserve_hour.minute < Calendar.getInstance().get(Calendar.MINUTE) + 30
                            || today && dialog.reserve_hour.hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1 && dialog.reserve_hour.minute < Calendar.getInstance().get(Calendar.MINUTE) - 30)
                    {
                        Log.e("Reserve Error", "Reserve must be done at least 30 minutes earlier!")
                        showToastMessage("error", "Reserve must be done at least 30 minutes earlier!")
                    }
                     else{
                        Log.i("Reservation Confirmed", "Reservation at " + hour + " confirmed!")
                        showToastMessage("success", "Reservation at " + hour + " confirmed!")

                        val db =
                            Room.databaseBuilder(
                                applicationContext,
                                ReserveDatabase::class.java,
                                "reserveDatabase"
                            )
                                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                                .fallbackToDestructiveMigration().build()
                        val reserveDao = db.reserveDao()

                        reserveDao.insertAll(
                            Reserve(
                                phone = dialog.reserve_phone.text.toString(),
                                date = date,
                                hour = hour,
                                ubication = restaurantUbication,
                                assistants = dialog.reserve_assistants.text.toString()
                            )
                        )

                        dialog.dismiss()
                        var intent = Intent(this, ClientMenuActivity::class.java)
                        startActivity(intent)
                    }
                }catch(e: NumberFormatException) {
                    if (correctNumber){
                        Log.e("Reserve Error","Invalid format input for assistants -- " + e.toString()
                        )
                        showToastMessage("error", "Assistants parameter must be a positive number!!")
                    }else{
                        Log.e("Reserve Error", "Phone Number is not a valid number! -- " + e.toString())
                        showToastMessage("error", "Phone Number is not a valid number!")
                    }
                }
            }
            dialog.btn_reserve_hour_cancel.setOnClickListener{
                dialog.dismiss()
            }
            dialog.show()
        }
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