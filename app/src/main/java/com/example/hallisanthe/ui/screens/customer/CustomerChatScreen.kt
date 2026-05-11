package com.example.hallisanthe.ui.screens.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush

data class ChatItem(
    val vendorName: String,
    val lastMessage: String
)

@Composable
fun CustomerChatScreen(
    autoMessage: String = "",
    autoOpenChat: Boolean = false
) {

    var selectedChat by remember {

        mutableStateOf<ChatItem?>(
            if (autoOpenChat)

                ChatItem(
                    "artisan.vendor@gmail.com",
                    "Hello 👋"
                )

            else null
        )
    }

    var message by remember {
        mutableStateOf(autoMessage)
    }
    var sentMessage by remember {
        mutableStateOf("")
    }
    var vendorReply by remember {
        mutableStateOf("")
    }
    var chats by remember {

        mutableStateOf(
            mutableListOf(

                ChatItem(
                    "handicrafts.shop@gmail.com",
                    "Your order is ready."
                ),

                ChatItem(
                    "pottery.store@gmail.com",
                    "Thanks for contacting us."
                ),

                ChatItem(
                    "rural.crafts@gmail.com",
                    "We have new arrivals."
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F5))
    ) {

        // 🔥 Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFFF8A65),
                            Color(0xFFFF7043)
                        )
                    )
                )
                .padding(
                    vertical = 28.dp,
                    horizontal = 20.dp
                )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "💬",

                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Messages",

                    style = MaterialTheme.typography.headlineMedium,

                    color = Color.White,

                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Connect with local artisans seamlessly",

                    color = Color.White.copy(alpha = 0.92f),

                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

        LazyColumn(
            contentPadding = PaddingValues(16.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(chats) { chat ->

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedChat = chat
                        },

                    shape = RoundedCornerShape(24.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(55.dp)
                                .background(
                                    Color(0xFFFFCCBC),
                                    CircleShape
                                ),

                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Person,
                                null
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = chat.vendorName,

                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(chat.lastMessage)
                        }

                        IconButton(
                            onClick = {

                                chats =
                                    chats.toMutableList().apply {
                                        remove(chat)
                                    }
                            }
                        ) {

                            Icon(
                                Icons.Default.Delete,
                                null,

                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }

        // 💬 Floating Chat UI
        if (selectedChat != null) {

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(20.dp)
                    .align(Alignment.Center),

                shape = RoundedCornerShape(26.dp)
            ) {

                Column {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFFF7043)
                            )
                            .padding(16.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = selectedChat!!.vendorName,

                            color = Color.White,

                            modifier = Modifier.weight(1f),

                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = {
                                selectedChat = null
                            }
                        ) {

                            Icon(
                                Icons.Default.Close,
                                null,

                                tint = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFFFFF3E0)
                            )
                        ) {

                            Text(
                                text =
                                    "Hello 👋 How can we help you?",

                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        if (sentMessage.isNotBlank()) {

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.End
                            ) {

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFFF7043)
                                    ),

                                    shape = RoundedCornerShape(18.dp)
                                ) {

                                    Text(
                                        text = sentMessage,

                                        modifier = Modifier.padding(14.dp),

                                        color = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFFFF3E0)
                                    ),

                                    shape = RoundedCornerShape(18.dp)
                                ) {

                                    Text(
                                        text = vendorReply,

                                        modifier = Modifier.padding(14.dp)
                                    )
                                }
                            }
                        }
                        OutlinedTextField(
                            value = message,

                            onValueChange = {
                                message = it
                            },

                            modifier = Modifier.fillMaxWidth(),

                            placeholder = {
                                Text("Type a message")
                            }
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {

                                if (message.isNotBlank()) {

                                    sentMessage = message

                                    val lowerMessage = message.lowercase()

                                    vendorReply = when {

                                        "stock" in lowerMessage -> {
                                            "Yes, we are currently in stock 😊"
                                        }

                                        "bulk" in lowerMessage -> {
                                            "I need to check bulk availability."
                                        }

                                        "payment" in lowerMessage -> {
                                            "The QR code will be sent via SMS soon."
                                        }

                                        "price" in lowerMessage -> {
                                            "Please check the product page for latest pricing."
                                        }

                                        "delivery" in lowerMessage -> {
                                            "Delivery usually takes 3-5 business days 🚚"
                                        }

                                        "location" in lowerMessage -> {
                                            "We are based in Karnataka, India 🌾"
                                        }

                                        "custom" in lowerMessage -> {
                                            "Yes! Custom handmade orders are possible."
                                        }

                                        "discount" in lowerMessage -> {
                                            "Discounts may be available on larger orders 😊"
                                        }

                                        else -> {
                                            "Thank you for contacting us! We'll respond soon 😊"
                                        }
                                    }

                                    message = ""
                                }
                            },

                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Icon(
                                Icons.Default.Send,
                                null
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Send")
                        }
                    }
                }
            }
        }
    }
}}