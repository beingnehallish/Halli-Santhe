package com.example.hallisanthe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.hallisanthe.data.AppDatabase
import com.example.hallisanthe.navigation.AppNavigation
import com.example.hallisanthe.ui.theme.HalliSantheTheme

class MainActivity : ComponentActivity() {

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "hallisanthe_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        setContent {
            HalliSantheTheme {
                AppNavigation(db)
            }
        }
    }
}