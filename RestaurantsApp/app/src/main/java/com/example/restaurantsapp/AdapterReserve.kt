package com.example.restaurantsapp

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.room.Room
import kotlinx.android.synthetic.main.list_reserves_view.view.*

class AdapterReserve: BaseAdapter {
    lateinit var context: Context
    lateinit var reserves: List<Reserve>

    constructor (context: Context, reserves: List<Reserve>): super(){
        this.context = context
        this.reserves = reserves
    }

    override fun getCount(): Int {
        return reserves.size
    }

    override fun getItem(position: Int): Reserve {
        return reserves[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.list_reserves_view, null, true)
        val reserveRestaurantNameTextView = itemView.reserve_restaurant_name as TextView
        val reserveRestaurantUbicationTextView = itemView.reserve_restaurant_ubication as TextView
        val reserveHourTextView = itemView.reserve_hour as TextView
        val reserveDateTextView = itemView.reserve_date as TextView
        val reserveAssistantsTextView = itemView.reserve_assistants as TextView
        val reserveRestaurantPhoneTextView = itemView.reserve_restaurant_phone as TextView
        if(position % 2 == 0) itemView.setBackgroundColor((Color.GRAY))
        else itemView.setBackgroundColor((Color.WHITE))

        val db =
            Room.databaseBuilder(context, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()
        val restaurant: Restaurant = restaurantDao.getRestaurantByUbication(getItem(position).ubication)

        try{
            reserveHourTextView.text = getItem(position).hour
            reserveRestaurantUbicationTextView.text = getItem(position).ubication
            reserveDateTextView.text = getItem(position).date
            reserveAssistantsTextView.text = "Number of people: " + getItem(position).assistants
            reserveRestaurantNameTextView.text = restaurant.name
            reserveRestaurantPhoneTextView.text = restaurant.phone
        }catch(e: Exception) {
            Log.e("Adapter Error", e.toString())
        }
        return itemView
    }
}