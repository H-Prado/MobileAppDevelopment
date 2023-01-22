package com.example.mad

import FlowersAdapter
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.input_box_layout.*

class ListActivity : AppCompatActivity() {

    private val listOfFlowers = ArrayList<String>()
    private val listOfFlowersImageIDs = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initializeFlowerList()

        var flowersAdapter = FlowersAdapter(this, listOfFlowers, listOfFlowersImageIDs)
        flower_list.adapter = flowersAdapter
        flower_list.setOnItemClickListener{ parent, view, position, id -> showFlowerNameInputBox(listOfFlowers[position], position, flowersAdapter)}
    }

    private fun showFlowerNameInputBox(oldName: String, position: Int, flowersAdapter: FlowersAdapter) {
        val dialog = Dialog(this)
        dialog.setTitle("Modify Flower name")
        dialog.setContentView(R.layout.input_box_layout)
        dialog.input_text.setText(oldName)
        dialog.btn_update.setOnClickListener{
            listOfFlowers[position] = dialog.input_text.text.toString()
            (flowersAdapter as BaseAdapter).notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun initializeFlowerList() {
        listOfFlowers.add("Tulipan");
        listOfFlowers.add("Rosa");
        listOfFlowers.add("Girasol");
        listOfFlowers.add("Lirio");
        listOfFlowers.add("Dalia");
        listOfFlowers.add("Margarita");
        listOfFlowers.add("Tulipán");
        listOfFlowers.add("Orquídea");
        listOfFlowers.add("Crisantemo");
        listOfFlowers.add("Narciso");
        listOfFlowers.add("Gardenia");
        listOfFlowers.add("Iris");
        listOfFlowers.add("Amapola");
        listOfFlowers.add("Lantana");
        listOfFlowers.add("Calandria");
        listOfFlowersImageIDs.add(R.drawable.tulipan)
        listOfFlowersImageIDs.add(R.drawable.rosa)
        listOfFlowersImageIDs.add(R.drawable.girasol)
        listOfFlowersImageIDs.add(R.drawable.lirio)
        listOfFlowersImageIDs.add(R.drawable.dalia)
        listOfFlowersImageIDs.add(R.drawable.margarita)
        listOfFlowersImageIDs.add(R.drawable.tulipan)
        listOfFlowersImageIDs.add(R.drawable.orquidea)
        listOfFlowersImageIDs.add(R.drawable.crisantemo)
        listOfFlowersImageIDs.add(R.drawable.narciso)
        listOfFlowersImageIDs.add(R.drawable.gardenia)
        listOfFlowersImageIDs.add(R.drawable.iris)
        listOfFlowersImageIDs.add(R.drawable.amapola)
        listOfFlowersImageIDs.add(R.drawable.lantana)
        listOfFlowersImageIDs.add(R.drawable.calandria)
    }
}