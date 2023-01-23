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
import kotlinx.android.synthetic.main.activity_bar_list.*
import kotlinx.android.synthetic.main.activity_restaurant_create.*
import kotlinx.android.synthetic.main.dialog_restaurant_modify.*
import kotlinx.android.synthetic.main.dialog_restaurant_owner.*
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*

class OwnerBarList : AppCompatActivity() {

    private var userName: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_bar_list)

        //Read sp values
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        userName = sp.getString("user_name", "")!!

        loadRestaurantsList()
    }

    fun runRegisterRestaurant(view: View){
        var intent = Intent(this, RestaurantCreateActivity::class.java)
        startActivity(intent)
    }

    fun showRestaurantDialog(restaurant: Restaurant, position: Int, adapterRestaurant: AdapterRestaurant){
        val dialog = Dialog(this)
        dialog.setTitle("Restaurant Dialog")
        dialog.setContentView(R.layout.dialog_restaurant_owner)
        dialog.btn_restaurant_edit.setOnClickListener{
            runEditRestaurant(restaurant)
            dialog.dismiss()
        }
        dialog.btn_restaurant_delete.setOnClickListener{
            runRemoveRestaurant(restaurant.ubication)
            loadRestaurantsList()
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
            if(paramsChecker(Restaurant(ubication = editDialog.restaurant_edit_ubication.text.toString(),
                    name = editDialog.restaurant_edit_name.text.toString(),
                    phone = editDialog.restaurant_edit_phone.text.toString(),
                    owner = userName), oldRestaurant)){
                loadRestaurantsList()
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

    fun loadRestaurantsList(){
        //Load Restaurants Databases
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()
        var listOfRestaurants: List<Restaurant> = restaurantDao.getRestaurantsByOwner(userName)

        var adapterRestaurant = AdapterRestaurant(this, listOfRestaurants)
        list_restaurants.adapter = adapterRestaurant

        list_restaurants.setOnItemClickListener{parent, view, position, id -> showRestaurantDialog(listOfRestaurants[position], position, adapterRestaurant)}
    }

    fun paramsChecker(newRestaurant: Restaurant, oldRestaurant: Restaurant): Boolean {
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()

        try{
            newRestaurant.phone?.toBigInteger()
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