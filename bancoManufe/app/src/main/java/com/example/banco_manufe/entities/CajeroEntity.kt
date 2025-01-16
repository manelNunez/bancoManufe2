package com.example.banco_manufe.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cajeros")
data class CajeroEntity (

    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val direccion:String?,
    val latitud:Double?,
    val longitud:Double?,
    val zoom:String?

)