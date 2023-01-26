package com.example.restaurantsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room

class AdminMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)
    }

    fun runReservesAdminMenu(view: View){

    }

    fun runRerestaurantsAdminMenu(view: View){

    }

    fun runInitialization(view: View){
        insertOwners()
        insertRestaurants()
        insertReserves()
    }

    fun insertOwners(){
        val db =
            Room.databaseBuilder(applicationContext, UserDatabase::class.java, "userDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val userDao = db.userDao()

        userDao.deleteAll()

        userDao.insertAll(User(userName = "hugo", password = "passhugo"))
        userDao.insertAll(User(userName = "leszlek", password = "passleszlek"))
        userDao.insertAll(User(userName = "paula", password = "paulapass"))
        userDao.insertAll(User(userName = "silvia", password = "silviapass"))
        userDao.insertAll(User(userName = "robert", password = "robertpass"))

        Log.i("Users Database", "Users Database deleded and initialized")
    }

    fun insertRestaurants(){
        val db =
            Room.databaseBuilder(applicationContext, RestaurantDatabase::class.java, "restaurantDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val restaurantDao = db.restaurantDao()

        restaurantDao.deleteAll()

        restaurantDao.insertAll(Restaurant(ubication = "vigo", name = "Hugo at Vigo", phone = "666888661", owner = "hugo"))
        restaurantDao.insertAll(Restaurant(ubication = "oxford", name = "Hugo at Oxford", phone = "666888662", owner = "hugo"))
        restaurantDao.insertAll(Restaurant(ubication = "dublin", name = "Hugo at Dublin", phone = "666888663", owner = "hugo"))
        restaurantDao.insertAll(Restaurant(ubication = "london", name = "Hugo at London", phone = "666888664", owner = "hugo"))
        restaurantDao.insertAll(Restaurant(ubication = "frankfurt", name = "Hugo at Frankfurt", phone = "666888665", owner = "hugo"))
        restaurantDao.insertAll(Restaurant(ubication = "vilar de Mato", name = "MatoP", phone = "111222331", owner = "paula"))
        restaurantDao.insertAll(Restaurant(ubication = "mexico", name = "MexicoP", phone = "111222332", owner = "paula"))
        restaurantDao.insertAll(Restaurant(ubication = "estrada", name = "EstradaP", phone = "111222333", owner = "paula"))
        restaurantDao.insertAll(Restaurant(ubication = "santiago de Compostela", name = "SantiagoP", phone = "111222334", owner = "paula"))
        restaurantDao.insertAll(Restaurant(ubication = "cuntis", name = "CuntisP", phone = "111222335", owner = "paula"))
        restaurantDao.insertAll(Restaurant(ubication = "amman", name = "Amman Eats", phone = "999888991", owner = "silvia"))
        restaurantDao.insertAll(Restaurant(ubication = "stockoholm", name = "Stockoholm Eats", phone = "999888992", owner = "silvia"))
        restaurantDao.insertAll(Restaurant(ubication = "oslo", name = "Oslo Eats", phone = "999888993", owner = "silvia"))
        restaurantDao.insertAll(Restaurant(ubication = "copenhagen", name = "Copenhagen Eats", phone = "999888994", owner = "silvia"))
        restaurantDao.insertAll(Restaurant(ubication = "malmo", name = "Malmo Eats", phone = "999888995", owner = "silvia"))
        restaurantDao.insertAll(Restaurant(ubication = "poznan", name = "Poznan Leszlek", phone = "555444331", owner = "leszlek"))
        restaurantDao.insertAll(Restaurant(ubication = "warsaw", name = "Warsaw Leszlek", phone = "555444332", owner = "leszlek"))
        restaurantDao.insertAll(Restaurant(ubication = "wroclaw", name = "Wroclaw Leszlek", phone = "555444333", owner = "leszlek"))
        restaurantDao.insertAll(Restaurant(ubication = "krakow", name = "Krakow Leszlek", phone = "555444334", owner = "leszlek"))
        restaurantDao.insertAll(Restaurant(ubication = "gdanks", name = "Gdansk", phone = "555444335", owner = "leszlek"))

        Log.i("Restaurants Database", "Restaurants Database deleded and initialized")
    }

    fun insertReserves(){
        val db =
            Room.databaseBuilder(applicationContext, ReserveDatabase::class.java, "reserveDatabase")
                .allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
        val reserveDao = db.reserveDao()

        reserveDao.deleteAll()

        reserveDao.insertAll(Reserve(date="30/02/2023", hour="14:00", phone="123456789", ubication="Krakow", assistants="3"))
        reserveDao.insertAll(Reserve(date="14/03/2023", hour="12:30", phone="123456789", ubication="Poznan", assistants="2"))
        reserveDao.insertAll(Reserve(date="01/04/2023", hour="15:00", phone="123456789", ubication="Vigo", assistants="13"))
        reserveDao.insertAll(Reserve(date="22/05/2023", hour="21:00", phone="123456789", ubication="Oslo Eats", assistants="8"))
        reserveDao.insertAll(Reserve(date="25/10/2023", hour="20:30", phone="123456789", ubication="MatoP", assistants="5"))
        Log.i("Reserves Database", "Reserves Database deleded and initialized")
    }
}