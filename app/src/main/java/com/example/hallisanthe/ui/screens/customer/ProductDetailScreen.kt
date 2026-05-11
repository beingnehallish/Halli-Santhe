package com.example.hallisanthe.ui.screens.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hallisanthe.data.Product

@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit,
    onContactSeller: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFF8F5))
    ) {

        // 📷 Product Image
        Box {

            AsyncImage(
                model = product.imageUri,

                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),

                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    onBack()
                },

                modifier = Modifier
                    .padding(12.dp)
                    .background(
                        Color.White.copy(alpha = 0.7f),
                        RoundedCornerShape(14.dp)
                    )
            ) {

                Icon(
                    Icons.Default.ArrowBack,
                    null
                )
            }
        }

        // 📦 Product Details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {

            AssistChip(
                onClick = {},

                label = {
                    Text(product.category)
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = product.name,

                style = MaterialTheme.typography.headlineMedium,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "₹${product.price}",

                style = MaterialTheme.typography.headlineSmall,

                color = Color(0xFFFF7043),

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(
                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Product Description",

                        fontWeight = FontWeight.Bold,

                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text =
                            "Authentic handmade artisan product carefully crafted by skilled village creators using traditional techniques."
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 🚀 Contact Seller Button
            Button(
                onClick = {
                    onContactSeller()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(20.dp)
            ) {

                Icon(
                    Icons.Default.Chat,
                    null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Contact Seller",

                    style = MaterialTheme.typography.titleMedium
                )
            }



        }
    }
}