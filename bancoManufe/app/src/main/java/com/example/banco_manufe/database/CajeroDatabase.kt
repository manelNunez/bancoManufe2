package com.example.banco_manufe.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.banco_manufe.dao.CajeroDao
import com.example.banco_manufe.entities.CajeroEntity

@Database(entities = [CajeroEntity::class], version = 1)
abstract class CajeroDatabase : RoomDatabase() {
    abstract fun cajeroDao(): CajeroDao
}
