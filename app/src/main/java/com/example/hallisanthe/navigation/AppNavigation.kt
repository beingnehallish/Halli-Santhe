package com.example.hallisanthe.navigation

import androidx.compose.runtime.*
import com.example.hallisanthe.data.AppDatabase
import com.example.hallisanthe.ui.screens.*
import com.example.hallisanthe.ui.screens.customer.CustomerMainScreen
import com.example.hallisanthe.ui.screens.vendor.VendorHomeScreen

@Composable
fun AppNavigation(db: AppDatabase) {

    var screen by remember { mutableStateOf("splash") }

    var role by remember { mutableStateOf("") }

    var loggedInEmail by remember {
        mutableStateOf("")
    }

    when (screen) {

        "splash" -> SplashScreen {
            screen = "landing"
        }

        "landing" -> RoleSelectionScreen(
            onVendorClick = {
                role = "vendor"
                screen = "auth"
            },
            onCustomerClick = {
                role = "customer"
                screen = "auth"
            }
        )

        "auth" -> AuthScreen(
            db = db,
            role = role,

            onLoginSuccess = { email ->

                loggedInEmail = email

                screen = if (role == "vendor") {
                    "vendor_home"
                } else {
                    "customer_home"
                }
            },

            onBack = {
                screen = "landing"
            }
        )

        "vendor_home" -> VendorHomeScreen(
            db = db,
            userEmail = loggedInEmail,
            onLogout = {
                screen = "landing"
            }
        )

        "customer_home" -> CustomerMainScreen(
            db = db,
            userEmail = loggedInEmail,
            onLogout = {
                screen = "landing"
            }
        )
    }
}