package com.example.hallisanthe.ui.screens.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hallisanthe.data.AppDatabase
import com.example.hallisanthe.data.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(
    db: AppDatabase,
    modifier: Modifier = Modifier
) {

    val dao = db.productDao()

    val scope = rememberCoroutineScope()

    var products by remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    var selectedProduct by remember {
        mutableStateOf<Product?>(null)
    }

    var openChats by remember {
        mutableStateOf(false)
    }
    var autoOpenChat by remember {
        mutableStateOf(false)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    var sortOption by remember {
        mutableStateOf("Default")
    }

    val sortOptions = listOf(
        "Default",
        "Price Low to High",
        "Price High to Low"
    )

    var sortExpanded by remember {
        mutableStateOf(false)
    }

    val categories = listOf(
        "All",
        "Handicrafts",
        "Pottery",
        "Textiles",
        "Jewelry",
        "Toys",
        "Home Decor"
    )

    LaunchedEffect(Unit) {

        scope.launch {
            products = dao.getAllProducts()
        }
    }

    if (selectedProduct != null) {

        ProductDetailScreen(
            product = selectedProduct!!,

            onBack = {
                selectedProduct = null
            },

            onContactSeller = {

                openChats = true

                autoOpenChat = true

                selectedProduct = null
            }
        )
    }

    else if (openChats) {

        CustomerChatScreen(
            autoMessage = "Hi, I'm interested in this product.",

            autoOpenChat = autoOpenChat
        )
    }

    else {

        var filteredProducts = products.filter {

            val matchesSearch =
                it.name.contains(searchQuery, true)

            val matchesCategory =
                selectedCategory == "All" ||
                        it.category == selectedCategory

            matchesSearch && matchesCategory
        }

        filteredProducts = when (sortOption) {

            "Price Low to High" -> {
                filteredProducts.sortedBy {
                    it.price.toIntOrNull() ?: 0
                }
            }

            "Price High to Low" -> {
                filteredProducts.sortedByDescending {
                    it.price.toIntOrNull() ?: 0
                }
            }

            else -> filteredProducts
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                    .padding(20.dp)
            ) {

                Column {

                    Text(
                        text = "Explore Village Products 🌾",

                        style = MaterialTheme.typography.headlineMedium,

                        color = Color.White,

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =
                            "Discover handmade creations from local artisans",

                        color = Color.White.copy(alpha = 0.92f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = searchQuery,

                        onValueChange = {
                            searchQuery = it
                        },

                        leadingIcon = {
                            Icon(Icons.Default.Search, null, tint=Color.White)
                        },

                        placeholder = {
                            Text("Search Products", color=Color.White.copy(alpha=0.85f))
                        },


                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color.White,

                            unfocusedBorderColor = Color.White.copy(alpha = 0.8f),

                            cursorColor = Color.White,

                            focusedTextColor = Color.White,

                            unfocusedTextColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp),

                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 🏷️ Categories
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp),

                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                categories.forEach { category ->

                    FilterChip(
                        selected =
                            selectedCategory == category,

                        onClick = {
                            selectedCategory = category
                        },

                        label = {
                            Text(category)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔥 Sort
            ExposedDropdownMenuBox(
                expanded = sortExpanded,

                onExpandedChange = {
                    sortExpanded = !sortExpanded
                }
            ) {

                OutlinedTextField(
                    value = sortOption,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Sort By")
                    },

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),

                    shape = RoundedCornerShape(18.dp)
                )

                ExposedDropdownMenu(
                    expanded = sortExpanded,

                    onDismissRequest = {
                        sortExpanded = false
                    }
                ) {

                    sortOptions.forEach {

                        DropdownMenuItem(
                            text = {
                                Text(it)
                            },

                            onClick = {

                                sortOption = it

                                sortExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // ❌ Empty State
            if (filteredProducts.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No Products Available 🌾"
                    )
                }
            }

            else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),

                    modifier = Modifier.height(
                        (((filteredProducts.size / 2) + 1) * 360).dp
                    ),

                    contentPadding = PaddingValues(12.dp),

                    verticalArrangement = Arrangement.spacedBy(12.dp),

                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(filteredProducts) { product ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(360.dp),

                            shape = RoundedCornerShape(24.dp),

                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {

                            Column {

                                Box {

                                    AsyncImage(
                                        model = product.imageUri,

                                        contentDescription = null,

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(170.dp),

                                        contentScale = ContentScale.Crop
                                    )


                                }

                                Column(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth()) {

                                    Text(
                                        text = product.name,

                                        style =
                                            MaterialTheme.typography.titleMedium,

                                        fontWeight = FontWeight.Bold,

                                        maxLines = 2
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "₹${product.price}",

                                        color = Color(0xFFFF7043),

                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    AssistChip(
                                        onClick = {},

                                        label = {
                                            Text(product.category)
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Button(
                                        onClick = {
                                            selectedProduct = product
                                        },

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),

                                        shape = RoundedCornerShape(18.dp)
                                    ) {

                                        Text(
                                            text = "View Product",

                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}