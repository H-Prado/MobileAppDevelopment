package com.example.barapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_restaurant_create.*
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*

class RestaurantCreateActivity : AppCompatActivity() {

    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_create)

        //Read sp values
        val sp = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        userName = sp.getString("user_name", "")!!
    }

    fun runConfirm(view: View){
        val db = Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
            .allowMainThreadQueries().enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()

        try{
            //Check if phone is a Int number
            restaurant_phone.text.toString().toBigInteger()

            //Check if no empty values
            if(!restaurant_ubication.text.isNullOrBlank() &&
               !restaurant_name.text.isNullOrBlank() &&
               !restaurant_phone.text.isNullOrBlank()){
                //Verify that phone has just 9 numbers
                if (restaurant_phone.text.toString().length == 9) {
                    val modifyRestaurant = Restaurant(
                        ubication = restaurant_ubication.text.toString(),
                        name = restaurant_name.text.toString(),
                        phone = restaurant_phone.text.toString(),
                        owner = userName
                    )
                    restaurantDao.insertAll(modifyRestaurant)
                    Log.i("Modified Succesfully", "Data: " + modifyRestaurant.toString())

                    showToastMessage("success", "Restaurant was succesfully created!")

                var intent = Intent(this, OwnerBarList::class.java)
                    startActivity(intent)
                }else{
                    Log.e("Error modifing:", "Phone number doesn't have 9 numbers!")
                    showToastMessage("error", "Phone number doesn't have 9 numbers!")
                    restaurant_info_message.text = "Phone number doesn't have 9 numbers!"
                }
            }else{
                Log.e("Error modifing:", "One parameter is missing!")
                showToastMessage("error", "One parameter is missing!")
                restaurant_info_message.text = "One parameter is missing!!"
            }
        }catch(e: NumberFormatException){
            Log.e("Error modifing:", "Phone Number is not a valid number! -- " + e.toString())
            showToastMessage("error", "Phone Number is not a valid number!")
            restaurant_info_message.text = "Phone Number is not a valid number!"
        }catch (e: Exception){
            Log.e("Error modifing:", "Restaurant for this ubication already exists!! -- " + e.toString())
            showToastMessage("error", "Restaurant for this ubication already exists!")
            restaurant_info_message.text = "Restaurant for this ubication already exists!"
        }
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