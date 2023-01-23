package com.example.restaurantsapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
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
        val reserveHourTextView = itemView.reserve_hour as TextView
        if(position % 2 == 0) itemView.setBackgroundColor((Color.GRAY))
        else itemView.setBackgroundColor((Color.WHITE))
        reserveHourTextView.text = getItem(position).hour
        return itemView
    }
}