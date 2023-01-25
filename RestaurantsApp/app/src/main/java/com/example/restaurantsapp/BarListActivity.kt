package com.example.restaurantsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_bar_list.*

class BarListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_list)

        //Load Restaurants Databases
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()
        var listOfRestaurants: List<Restaurant> = restaurantDao.getAll()

        var adapterRestaurant = AdapterRestaurant(this, listOfRestaurants)
        list_restaurants.adapter = adapterRestaurant

        list_restaurants.setOnItemClickListener{parent, view, position, id -> runReserveActivity(listOfRestaurants[position], position, adapterRestaurant)}
    }

    fun runReserveActivity(restaurant: Restaurant, position: Int, adapterRestaurant: AdapterRestaurant){
        var intent = Intent(this, ReserveCreateActivity::class.java)
        intent.putExtra("restaurantUbicationToPass", restaurant.ubication)
        startActivity(intent)
    }
}