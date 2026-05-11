package com.example.hallisanthe.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val price: String,
    val category: String,
    val imageUri: String
)