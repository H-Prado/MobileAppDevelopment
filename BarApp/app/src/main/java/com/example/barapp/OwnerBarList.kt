package com.example.barapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_bar_list.*
import kotlinx.android.synthetic.main.dialog_restaurant_owner.*

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
        dialog.setTitle("Edit Restaurant")
        dialog.setContentView(R.layout.dialog_restaurant_owner)
        dialog.btn_restaurant_edit.setOnClickListener{
            var intent = Intent(this, RestaurantModifyActivity::class.java)
            startActivity(intent)
        }
        dialog.btn_restaurant_delete.setOnClickListener{
            runRemoveRestaurant(restaurant.ubication)
            loadRestaurantsList()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun runEditRestaurant(restaurantUbication: String){
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
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
}