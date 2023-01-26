package com.example.restaurantsapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_admin_list.*
import kotlinx.android.synthetic.main.dialog_filter.*
import kotlinx.android.synthetic.main.dialog_interact.*
import kotlinx.android.synthetic.main.dialog_reserve_modify.*
import kotlinx.android.synthetic.main.dialog_restaurant_modify.*
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*
import java.text.SimpleDateFormat
import java.util.*

class AdminListActivity : AppCompatActivity() {
    var typeListPassed = ""
    var filter = "none"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)
        filter = "none"

        typeListPassed = intent.getStringExtra("typeListPassed")!!


        if(typeListPassed.equals("reserve")){
            btn_admin_filter.text = "Filter by phone number"
            loadReserveList("none")
        }
        if(typeListPassed.equals("restaurant")){
            btn_admin_filter.text = "Filter by owner"
            loadRestaurantsList("none")
        }
    }

    fun runFilterList(view: View){
        if(filter.equals("none")) {
            if (typeListPassed.equals("restaurant")) {
                val dialog = Dialog(this)
                dialog.setTitle("Filter Dialog")
                dialog.setContentView(R.layout.dialog_filter)
                dialog.filter_text.text = "Name of the owner"
                dialog.btn_filter_confirm.setOnClickListener {
                    filter = dialog.filter_input.text.toString()
                    loadRestaurantsList(filter)
                    btn_admin_filter.text = "Cancel Filter"
                    dialog.dismiss()
                }
                dialog.btn_filter_cancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
            if (typeListPassed.equals("reserve")) {
                val dialog = Dialog(this)
                dialog.setTitle("Filter Dialog")
                dialog.setContentView(R.layout.dialog_filter)
                dialog.filter_text.text = "Number phone"
                dialog.btn_filter_confirm.setOnClickListener {
                    filter = dialog.filter_input.text.toString()
                    loadReserveList(filter)
                    btn_admin_filter.text = "Cancel filter"
                    dialog.dismiss()
                }
                dialog.btn_filter_cancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        }else{
            if(typeListPassed.equals("restaurant")){
                btn_admin_filter.text = "Filter by owner"
                filter = "none"
                loadRestaurantsList(filter)
            }
            if(typeListPassed.equals("reserve")){
                btn_admin_filter.text = "Filter by phone number"
                filter = "none"
                loadReserveList(filter)
            }
        }
    }

    //Restaurants
    fun loadRestaurantsList(filter: String){
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()

        var listOfRestaurants: List<Restaurant>
        if(filter.equals("none")) {
            listOfRestaurants = restaurantDao.getAll()
        }else{
            listOfRestaurants = restaurantDao.getRestaurantsByOwner(filter)

        }
        var adapterRestaurant = AdapterRestaurant(this, listOfRestaurants)
        admin_list.adapter = adapterRestaurant

        admin_list.setOnItemClickListener{parent, view, position, id -> showRestaurantDialog(listOfRestaurants[position], position, adapterRestaurant)}
    }

    fun showRestaurantDialog(restaurant: Restaurant, position: Int, adapterRestaurant: AdapterRestaurant){
        val dialog = Dialog(this)
        dialog.setTitle("Restaurant Dialog")
        dialog.setContentView(R.layout.dialog_interact)
        dialog.interact_text.text = "What do you want to do with this restaurant?"
        dialog.btn_edit.setOnClickListener{
            runEditRestaurant(restaurant)
            dialog.dismiss()
        }
        dialog.btn_delete.setOnClickListener{
            runRemoveRestaurant(restaurant.ubication)
            loadRestaurantsList(filter)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun runEditRestaurant(restaurant: Restaurant){
        val oldRestaurant: Restaurant = restaurant
        val editDialog = Dialog(this)
        editDialog.setTitle("Edit Restaurant")
        editDialog.setContentView(R.layout.dialog_restaurant_modify)
        editDialog.restaurant_edit_ubication.setText(restaurant.ubication)
        editDialog.restaurant_edit_name.setText(restaurant.name)
        editDialog.restaurant_edit_phone.setText(restaurant.phone)
        editDialog.btn_restaurant_edit_confirm.setOnClickListener{
            if(paramsChecker(Restaurant(ubication = editDialog.restaurant_edit_ubication.text.toString().lowercase(),
                    name = editDialog.restaurant_edit_name.text.toString(),
                    phone = editDialog.restaurant_edit_phone.text.toString(),
                    owner = restaurant.owner), oldRestaurant)){
                loadRestaurantsList(filter)
                editDialog.dismiss()
            }
        }
        editDialog.btn_restaurant_edit_cancel.setOnClickListener{
            editDialog.dismiss()
        }
        editDialog.show()
    }

    fun runRemoveRestaurant(restaurantUbication: String){
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()

        restaurantDao.deleteByUbication(restaurantUbication)
        Log.i("Restaurant Deleted", "Restaurant at " + restaurantUbication + " was succesfully deleted")
    }

    fun paramsChecker(newRestaurant: Restaurant, oldRestaurant: Restaurant): Boolean {
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()

        try{
            newRestaurant.phone?.toInt()
            //Check if no empty values
            if(!newRestaurant.ubication.isNullOrBlank() &&
                !newRestaurant.name.isNullOrBlank() &&
                !newRestaurant.phone.isNullOrBlank()){
                //Verify that phone has just 9 numbers
                if (newRestaurant.phone.toString().length == 9) {
                    //verify that another different restaurant doesn't have the same ubication
                    if(restaurantDao.getRestaurantByUbication(newRestaurant.ubication)==null || oldRestaurant.ubication == newRestaurant.ubication){
                        restaurantDao.deleteByUbication(oldRestaurant.ubication)
                        restaurantDao.insertAll(newRestaurant)
                        Log.i("Modified Succesfully", "Data: " + newRestaurant.toString())

                        showToastMessage("success", "Restaurant was succesfully modified!")

                        return true
                    }
                    else{
                        Log.e("Error modifing:", "Restaurant on this location already exists!")
                        showToastMessage("error", "Restaurant on this location already exists!")
                    }
                }else{
                    Log.e("Error modifing:", "Phone number doesn't have 9 numbers!")
                    showToastMessage("error", "Phone number doesn't have 9 numbers!")
                }
            }else{
                Log.e("Error modifing:", "One parameter is missing!")
                showToastMessage("error", "One parameter is missing!")
            }
        }catch(e: NumberFormatException){
            Log.e("Error modifing:", "Phone Number is not a valid number! -- " + e.toString())
            showToastMessage("error", "Phone Number is not a valid number!")
        }catch (e: Exception){
            Log.e("Error modifing:", "Restaurant for this ubication already exists!! -- " + e.toString())
            showToastMessage("error", "Restaurant for this ubication already exists!")
        }
        return false
    }

    //Reserves
    fun loadReserveList(filter: String) {
        val db =
            Room.databaseBuilder(applicationContext, ReserveDatabase::class.java, "reserveDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val reserveDao = db.reserveDao()

        var listOfNotOrderedReserves: List<Reserve>
        if(filter.equals("none")) {
            listOfNotOrderedReserves = reserveDao.getAll()
        }
        else{
            listOfNotOrderedReserves = reserveDao.getReservesByPhone(filter)
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val listOfReserves = listOfNotOrderedReserves.sortedWith(compareBy({ dateFormat.parse(it.date).time }, { timeFormat.parse(it.hour).time }))

        var adapterReserve = AdapterReserve(this, listOfReserves)
        admin_list.adapter = adapterReserve

        admin_list.setOnItemClickListener{parent, view, position, id -> showReserveDialog(listOfReserves[position], position, adapterReserve)}
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
            loadReserveList(filter)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun runEditReserve(reserve: Reserve){
        val editDialog = Dialog(this)
        editDialog.setContentView(R.layout.dialog_reserve_modify)
        editDialog.reserve_edit_hour.setIs24HourView(true)
        editDialog.reserve_edit_text.setText("When will your new hor of arrival?\n (Only from now to 22:00)")
        editDialog.btn_reserve_edit_confirm.setOnClickListener{
            if(//If the reserve to change is on this day
                (reserve.date.substring(0,2).toInt() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                && reserve.date.substring(3,5).toInt() == Calendar.getInstance().get(Calendar.MONTH)
                && reserve.date.substring(6,10).toInt() == Calendar.getInstance().get(Calendar.YEAR))
                //The new hour can not be 30 mins from now or earlier
                && (editDialog.reserve_edit_hour.hour < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                || editDialog.reserve_edit_hour.hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && editDialog.reserve_edit_hour.minute < Calendar.getInstance().get(
                    Calendar.MINUTE) + 30
                || editDialog.reserve_edit_hour.hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1 && editDialog.reserve_edit_hour.minute < Calendar.getInstance().get(Calendar.MINUTE) - 30))
            {
                Log.e("Reserve Error", "Reserve must be at least 30 minutes from now or later!")
                showToastMessage("error", "Reserve must be at least 30 minutes from now or later!")
            }else if(editDialog.reserve_edit_hour.hour < 12 || editDialog.reserve_edit_hour.hour > 22
                    || (editDialog.reserve_edit_hour.hour == 22 && editDialog.reserve_edit_hour.minute != 0)){
                var hour =""
                if (editDialog.reserve_edit_hour.minute.toString().length == 1){
                    hour = editDialog.reserve_edit_hour.hour.toString() + ":0" + editDialog.reserve_edit_hour.minute
                }else {
                    hour = editDialog.reserve_edit_hour.hour.toString() + ":" + editDialog.reserve_edit_hour.minute
                }
                Log.e("Hour Error", hour + " is not a valid hour")
                showToastMessage("error", hour + " is not a valid hour")
            }else{
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

                loadReserveList(filter)

                showToastMessage("success", "Reserve was succesfully modified!")
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