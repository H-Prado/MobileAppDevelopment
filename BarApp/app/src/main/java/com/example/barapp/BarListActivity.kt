package com.example.barapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_bar_list.*

class BarListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_list)

        //Get Action in process
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userRole = sp.getString("user_role", "")
        val userAction = sp.getString("user_action", "")
        Log.i("User information: ", "Role:" + userRole!! + " -- Action: " + userAction!!)

        //Load Restaurants Databases
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()
        var listOfRestaurants: List<Restaurant> = restaurantDao.getAll()

        var adapterRestaurant = AdapterRestaurant(this, listOfRestaurants)
        list_restaurants.adapter = adapterRestaurant
    }
}