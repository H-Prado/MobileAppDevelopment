package com.example.restaurantsapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_reserve_list.*
import kotlinx.android.synthetic.main.dialog_interact.*
import kotlinx.android.synthetic.main.dialog_reserve_hour.*
import kotlinx.android.synthetic.main.dialog_reserve_modify.*
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReserveListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_list)
        val passedPhone = intent.getStringExtra("phoneToPass")!!
        Log.i("Passed Phone", passedPhone)


        loadReserveList(passedPhone)

    }

    fun showReserveDialog(reserve: Reserve, position: Int, adapterReserve: AdapterReserve){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_interact)
        dialog.interact_text.text = "What do you want to do with this Reserve?"
        dialog.btn_edit.setOnClickListener{
            runEditReserve(reserve)
            dialog.dismiss()
        }
        dialog.btn_delete.setOnClickListener{
            runRemoveReserve(reserve)
            loadReserveList(reserve.phone)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun runEditReserve(reserve: Reserve){
        val editDialog = Dialog(this)
        editDialog.setContentView(R.layout.dialog_reserve_modify)
        editDialog.reserve_edit_text.setText("When will your new hor of arrival?\n (Only from now to 22:00)")
        editDialog.btn_reserve_edit_confirm.setOnClickListener{
            if(editDialog.reserve_edit_hour.hour < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                || editDialog.reserve_edit_hour.hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && editDialog.reserve_edit_hour.minute < Calendar.getInstance().get(
                    Calendar.MINUTE) + 30
                || editDialog.reserve_edit_hour.hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1 && editDialog.reserve_edit_hour.minute < Calendar.getInstance().get(Calendar.MINUTE) - 30)
            {
                Log.e("Reserve Error", "Reserve must be at least 30 minutes from now or later!")
                showToastMessage("error", "Reserve must be at least 30 minutes from now or later!")
            }else {
                var newHour = ""
                if (editDialog.reserve_edit_hour.minute.toString().length == 1){
                    newHour = editDialog.reserve_edit_hour.hour.toString() + ":0" + editDialog.reserve_edit_hour.minute
                }else {
                    newHour = editDialog.reserve_edit_hour.hour.toString() + ":" + editDialog.reserve_edit_hour.minute
                }

                val db =
                    Room.databaseBuilder(applicationContext, ReserveDatabase::class.java, "reserveDatabase")
                        .allowMainThreadQueries().enableMultiInstanceInvalidation()
                        .fallbackToDestructiveMigration().build()
                val reserveDao = db.reserveDao()
                reserveDao.updateHour(reserve.reserveId, newHour)

                loadReserveList(reserve.phone)
                editDialog.dismiss()
            }
        }
        editDialog.btn_reserve_edit_cancel.setOnClickListener{
            editDialog.dismiss()
        }
        editDialog.show()
    }

    fun runRemoveReserve(reserve: Reserve){
        val db =
            Room.databaseBuilder(applicationContext, ReserveDatabase::class.java, "reserveDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val reserveDao = db.reserveDao()

        reserveDao.deleteByReserveId(reserve.reserveId)
        Log.i("Restaurant Deleted", "Restaurant of " + reserve.date + " at " + reserve.hour + "\nwas succesfully deleted")
    }

    fun loadReserveList(passedPhone: String) {
        val db =
            Room.databaseBuilder(applicationContext, ReserveDatabase::class.java, "reserveDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val reserveDao = db.reserveDao()
        var listOfNotOrderedReserves: List<Reserve> = reserveDao.getReservesByPhone(passedPhone)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val listOfReserves = listOfNotOrderedReserves.sortedWith(compareBy({ dateFormat.parse(it.date).time }, { timeFormat.parse(it.hour).time }))

        var adapterReserve = AdapterReserve(this, listOfReserves)
        list_reserves.adapter = adapterReserve

        list_reserves.setOnItemClickListener{parent, view, position, id -> showReserveDialog(listOfReserves[position], position, adapterReserve)}
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