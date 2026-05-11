package com.example.hallisanthe.ui.screens.customer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.hallisanthe.data.AppDatabase

@Composable
fun CustomerMainScreen(
    db: AppDatabase,
    userEmail: String,
    onLogout: () -> Unit
) {

    var selectedTab by remember {
        mutableStateOf(0)
    }

    Scaffold(

        bottomBar = {

            NavigationBar {

                // 🏠 HOME
                NavigationBarItem(
                    selected = selectedTab == 0,

                    onClick = {
                        selectedTab = 0
                    },

                    icon = {
                        Icon(Icons.Default.Home, null)
                    },

                    label = {
                        Text("Home")
                    }
                )

                // 💬 CHATS
                NavigationBarItem(
                    selected = selectedTab == 1,

                    onClick = {
                        selectedTab = 1
                    },

                    icon = {
                        Icon(Icons.Default.Chat, null)
                    },

                    label = {
                        Text("Chats")
                    }
                )

                // 👤 PROFILE
                NavigationBarItem(
                    selected = selectedTab == 2,

                    onClick = {
                        selectedTab = 2
                    },

                    icon = {
                        Icon(Icons.Default.Person, null)
                    },

                    label = {
                        Text("Me")
                    }
                )
            }
        }
    ) { padding ->

        when (selectedTab) {

            // 🏠 HOME SCREEN
            0 -> CustomerHomeScreen(
                db = db,

                modifier = Modifier.padding(padding)
            )

            // 💬 CHAT SCREEN
            1 -> CustomerChatScreen()

            // 👤 PROFILE SCREEN
            2 -> CustomerProfileScreen(
                db = db,

                userEmail = userEmail,

                onLogout = onLogout
            )
        }
    }
}