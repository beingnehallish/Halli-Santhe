package com.example.hallisanthe.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [User::class, Product::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun productDao(): ProductDao
}