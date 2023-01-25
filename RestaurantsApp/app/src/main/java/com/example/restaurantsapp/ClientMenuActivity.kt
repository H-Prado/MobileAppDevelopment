package com.example.restaurantsapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.dialog_reserve_modify_phone.*
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*

class ClientMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_menu)
    }

    fun runReservation(view: View) {
        var intent = Intent(this, BarListActivity::class.java)
        startActivity(intent)
    }
    fun runModify(view: View) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_reserve_modify_phone)
        dialog.btn_reserve_modify_confirm.setOnClickListener {

            try {
                var phone = dialog.reserve_modify_phone.text.toString()
                phone.toInt()
                if(phone.length != 9){
                    Log.e("Error modifing:", "Phone number doesn't have 9 numbers!")
                    showToastMessage("error", "Phone number doesn't have 9 numbers!")
                }
                else {
                    //Load Reserve Databases
                    val db =Room.databaseBuilder(applicationContext, ReserveDatabase::class.java, "reserveDatabase")
                            .allowMainThreadQueries().enableMultiInstanceInvalidation()
                            .fallbackToDestructiveMigration().build()
                    val reserveDao = db.reserveDao()
                    var listOfReserves: List<Reserve> = reserveDao.getReservesByPhone(phone)

                    if (listOfReserves.isEmpty()) {
                        Log.e("Reserve Error", "No reserves found for number: " + phone)
                        showToastMessage("error", "No reserves found for number: " + phone)
                    } else {
                        dialog.dismiss()

                        var intent = Intent(this, ReserveListActivity::class.java)
                        intent.putExtra("phoneToPass", phone)
                        startActivity(intent)
                    }
                }
            }catch(e: NumberFormatException){
                Log.e("Error modifing:", "Phone Number is not a valid number! -- " + e.toString())
                showToastMessage("error", "Phone Number is not a valid number!")
            }
        }
        dialog.btn_reserve_modify_cancel.setOnClickListener{
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