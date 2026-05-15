package com.example.hallisanthe.ui.screens.customer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallisanthe.data.AppDatabase
import kotlinx.coroutines.launch

@Composable
fun CustomerProfileScreen(
    db: AppDatabase,
    userEmail: String,
    onLogout: () -> Unit
) {

    val dao = db.userDao()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var newPassword by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFFF8A65),
                        Color(0xFFFF7043),
                        Color(0xFFFFCCBC)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            //Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,

                        modifier = Modifier.size(60.dp),

                        tint = Color(0xFFFF7043)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Customer Profile",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Manage your Halli Santhe account",
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Main Card
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),

                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                ),

                elevation = CardDefaults.elevatedCardElevation(12.dp)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    //Account Info
                    Text(
                        text = "Account Information",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = userEmail,

                        onValueChange = {},

                        readOnly = true,

                        label = {
                            Text("Logged In Email")
                        },

                        leadingIcon = {
                            Icon(Icons.Default.Email, null)
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    //Security Section
                    Text(
                        text = "Security",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = newPassword,

                        onValueChange = {
                            newPassword = it
                        },

                        label = {
                            Text("New Password")
                        },

                        leadingIcon = {
                            Icon(Icons.Default.Lock, null)
                        },

                        trailingIcon = {

                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,

                                    contentDescription = null
                                )
                            }
                        },

                        visualTransformation =
                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Change Password Button
                    Button(
                        onClick = {

                            if (newPassword.isBlank()) {

                                Toast.makeText(
                                    context,
                                    "Enter a new password",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            scope.launch {

                                dao.updatePassword(
                                    userEmail,
                                    newPassword
                                )

                                Toast.makeText(
                                    context,
                                    "Password Updated Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                newPassword = ""
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Text(
                            text = "Change Password",
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logout Button
                    OutlinedButton(
                        onClick = {
                            onLogout()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),

                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Logout")
                    }

                    Spacer(modifier = Modifier.height(55.dp))

                }
            }

            Spacer(modifier = Modifier.weight(76f))


        }
    }
}
