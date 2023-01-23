package com.example.barapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_restaurant_info.*

class RestaurantInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_info)
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
               !restaurant_phone.text.isNullOrBlank() &&
               !restaurant_owner.text.isNullOrBlank() &&
               !restaurant_password.text.isNullOrBlank()){
                //Verify that phone has just 9 numbers
                if (restaurant_phone.text.toString().length == 9) {
                    val modifyRestaurant = Restaurant(
                        ubication = restaurant_ubication.text.toString(),
                        name = restaurant_name.text.toString(),
                        phone = restaurant_phone.text.toString(),
                        owner = restaurant_owner.text.toString(),
                        password = restaurant_password.text.toString()
                    )
                    restaurantDao.insertAll(modifyRestaurant)
                    Log.i("Modified Succesfully", "Data: " + modifyRestaurant.toString())

                    showToastMessage()

                    var intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                }else{
                    Log.e("Error modifing:", "Phone number doesn't have 9 numbers!")
                    restaurant_info_message.text = "Phone number doesn't have 9 numbers!"
                }
            }else{
                Log.e("Error modifing:", "One parameter is missing!")
                restaurant_info_message.text = "One parameter is missing!!"
            }
        }catch(e: Exception){
            Log.e("Error modifing:", "Phone Number is not a valid number!" + e.toString())
            restaurant_info_message.text = "Phone number is not a valid number!"
        }
    }

    fun showToastMessage() {
        var toastView = layoutInflater.inflate(R.layout.toast_success_modify,null)
        var toast = Toast.makeText(this,"This is the toast message", Toast.LENGTH_LONG)
        toast.view = toastView
        toast.show()
    }
}