package com.example.barapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.list_restaurants_view.view.*

class RestaurantAdapter: BaseAdapter {
    lateinit var context: Context
    lateinit var restaurants: List<Restaurant>

    constructor (context: Context, restaurants: List<Restaurant>): super(){
        this.context = context
        this.restaurants = restaurants
    }

    override fun getCount(): Int {
        return restaurants.size
    }

    override fun getItem(position: Int): Restaurant {
        return restaurants[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.list_restaurants_view, null, true)
        val restaurantNameTextView = itemView.restaurant_name as TextView
        val restaurantPhoneTextView = itemView.restaurant_phone as TextView
        val restaurantUbicationTextView = itemView.restaurant_ubication as TextView
        val restaurantOwnerTextView = itemView.restaurant_owner as TextView
        if(position % 2 == 0) itemView.setBackgroundColor((Color.GRAY))
        else itemView.setBackgroundColor((Color.WHITE))
        restaurantNameTextView.text = getItem(position).name
        restaurantPhoneTextView.text = getItem(position).phone
        restaurantUbicationTextView.text = "-Location: " + getItem(position).ubication
        restaurantOwnerTextView.text = "-Owner: " + getItem(position).owner
        return itemView
    }

}