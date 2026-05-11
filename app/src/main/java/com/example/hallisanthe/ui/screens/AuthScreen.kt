package com.example.hallisanthe.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lint.kotlin.metadata.Visibility
import com.example.hallisanthe.data.AppDatabase
import com.example.hallisanthe.data.User
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    db: AppDatabase,
    role: String,
    onLoginSuccess: (String) -> Unit,
    onBack: () -> Unit
) {

    val dao = db.userDao()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var isLogin by remember {
        mutableStateOf(true)
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🌾 App Title
            Text(
                text = "Halli Santhe 🌾",
                fontSize = 34.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Empowering Local Artisans",
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔥 Auth Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(32.dp),

                elevation = CardDefaults.elevatedCardElevation(12.dp),

                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    // 👤 Role Header
                    Text(
                        text = if (isLogin)
                            "Login as ${role.replaceFirstChar { it.uppercase() }}"
                        else
                            "Create ${role.replaceFirstChar { it.uppercase() }} Account",

                        style = MaterialTheme.typography.headlineSmall,

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 📧 Email Field
                    OutlinedTextField(
                        value = email,

                        onValueChange = {
                            email = it
                        },

                        label = {
                            Text("Email")
                        },

                        leadingIcon = {
                            Icon(Icons.Default.Email, null)
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp),

                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔒 Password Field
                    OutlinedTextField(
                        value = password,

                        onValueChange = {
                            password = it
                        },

                        label = {
                            Text("Password")
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

                        shape = RoundedCornerShape(18.dp),

                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // 🚀 Main Button
                    Button(
                        onClick = {

                            if (
                                email.isBlank() ||
                                password.isBlank()
                            ) {

                                Toast.makeText(
                                    context,
                                    "Fill all fields",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            scope.launch {

                                try {

                                    if (isLogin) {

                                        val user = dao.login(
                                            email.trim(),
                                            password.trim(),
                                            role
                                        )

                                        if (user != null) {

                                            Toast.makeText(
                                                context,
                                                "Login Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            onLoginSuccess(email)

                                        } else {

                                            Toast.makeText(
                                                context,
                                                "Invalid Credentials",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    } else {

                                        dao.insertUser(
                                            User(
                                                email = email.trim(),
                                                password = password.trim(),
                                                role = role
                                            )
                                        )

                                        Toast.makeText(
                                            context,
                                            "Account Created",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        isLogin = true
                                    }

                                } catch (e: Exception) {

                                    Toast.makeText(
                                        context,
                                        e.message ?: "Error",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Text(
                            text =
                                if (isLogin)
                                    "Login"
                                else
                                    "Create Account",

                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 🔄 Toggle Login/Signup
                    TextButton(
                        onClick = {
                            isLogin = !isLogin
                        },

                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {

                        Text(
                            text =
                                if (isLogin)
                                    "Don't have an account? Sign Up"
                                else
                                    "Already have an account? Login"
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 🔙 Back Button
                    OutlinedButton(
                        onClick = {
                            onBack()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),

                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Text("Back")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 👨‍💻 Footer
            Text(
                text = "Made by Nehal Gupta",
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}