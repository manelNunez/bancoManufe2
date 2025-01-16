package com.example.banco_manufe.applications

import android.app.Application
import androidx.room.Room
import com.example.banco_manufe.database.CajeroDatabase

class CajeroApplication : Application() {

    companion object{
        lateinit var database: CajeroDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            CajeroDatabase::class.java,
            "CajeroDatabase")
            .build()
    }

}