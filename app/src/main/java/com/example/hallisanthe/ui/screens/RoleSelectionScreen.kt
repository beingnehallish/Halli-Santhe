package com.example.hallisanthe.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun RoleSelectionScreen(
    onVendorClick: () -> Unit,
    onCustomerClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Welcome to Halli Santhe 🛍️",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onVendorClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Are you a Vendor?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onCustomerClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Are you a Customer?")
        }
    }
}