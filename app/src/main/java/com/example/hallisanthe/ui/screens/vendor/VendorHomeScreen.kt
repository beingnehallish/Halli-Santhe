package com.example.hallisanthe.ui.screens.vendor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Edit
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import com.example.hallisanthe.data.AppDatabase
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.hallisanthe.ui.screens.vendor.VendorProfilePage
import com.example.hallisanthe.data.Product
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape

@Composable
fun VendorHomeScreen(
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

                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                    },
                    icon = {
                        Icon(Icons.Default.Home, null)
                    },
                    label = {
                        Text("Products")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    },
                    icon = {
                        Icon(Icons.Default.Info, null)
                    },
                    label = {
                        Text("About")
                    }
                )

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

        Box(
            modifier = Modifier.padding(padding)
        ) {
            when(selectedTab) {

                0 -> VendorProductsPage(db)

                1 -> VendorAboutPage()

                2 -> VendorProfilePage(
                    db = db,
                    userEmail = userEmail,
                    onLogout = onLogout
                )
            }

        }
    }
}

@Composable
fun VendorProductsPage(db: AppDatabase) {

    val dao = db.productDao()

    val scope = rememberCoroutineScope()

    var editingProduct by remember {
        mutableStateOf<Product?>(null)
    }

    var products by remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    var showAddProduct by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        scope.launch {
            products = dao.getAllProducts()
        }
    }

    // ✅ Add/Edit Screen
    if (showAddProduct || editingProduct != null) {

        AddProductScreen(
            db = db,

            existingProduct = editingProduct,

            onDone = {

                showAddProduct = false
                editingProduct = null

                scope.launch {
                    products = dao.getAllProducts()
                }
            }
        )
    }

    else {

        val filteredProducts = products.filter {

            it.name.contains(searchQuery, true)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8F5))
        ) {

            // 🔥 Premium Header
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
                    .padding(22.dp)
            ) {

                Column {

                    Text(
                        text = "Vendor Dashboard 🌾",

                        style = MaterialTheme.typography.headlineMedium,

                        color = Color.White,

                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =
                            "Manage artisan products beautifully",

                        color = Color.White.copy(alpha = 0.92f)
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // 🔍 Search
                    OutlinedTextField(
                        value = searchQuery,

                        onValueChange = {
                            searchQuery = it
                        },

                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                null,
                                tint = Color.White
                            )
                        },

                        placeholder = {
                            Text(
                                "Search Products",

                                color = Color.White.copy(alpha = 0.85f)
                            )
                        },

                        textStyle =
                            LocalTextStyle.current.copy(
                                color = Color.White
                            ),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color.White,

                            unfocusedBorderColor =
                                Color.White.copy(alpha = 0.8f),

                            cursorColor = Color.White,

                            focusedTextColor = Color.White,

                            unfocusedTextColor = Color.White
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(20.dp),

                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // 📊 Stats Row
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        DashboardStatCard(
                            title = "${products.size}",
                            subtitle = "Products"
                        )

                        DashboardStatCard(
                            title = "Active",
                            subtitle = "Stop Status"
                        )

                        DashboardStatCard(
                            title = "15",
                            subtitle = "Orders"
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ➕ Add Product Button
                    Button(
                        onClick = {
                            showAddProduct = true
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),

                        shape = RoundedCornerShape(20.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {

                        Icon(
                            Icons.Default.Add,
                            null,

                            tint = Color(0xFFFF7043)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Add New Product",

                            color = Color(0xFFFF7043),

                            style =
                                MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // ❌ Empty State
            if (filteredProducts.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),

                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "📦",

                            style =
                                MaterialTheme.typography.displayLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "No Products Found",

                            style =
                                MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            // 🛍️ Product Grid
            else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),

                    contentPadding = PaddingValues(14.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(14.dp),

                    horizontalArrangement =
                        Arrangement.spacedBy(14.dp)
                ) {

                    items(filteredProducts) { product ->

                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(340.dp),

                            shape = RoundedCornerShape(28.dp),

                            elevation =
                                CardDefaults.elevatedCardElevation(10.dp)
                        ) {

                            Column {

                                // 📷 Product Image
                                Box {

                                    AsyncImage(
                                        model = product.imageUri,

                                        contentDescription = null,

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),

                                        contentScale =
                                            ContentScale.Crop
                                    )

                                    // ✏️ Premium Edit Button
                                    Surface(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(10.dp),

                                        shape = CircleShape,

                                        color =
                                            Color.White.copy(alpha = 0.9f)
                                    ) {

                                        IconButton(
                                            onClick = {
                                                editingProduct =
                                                    product
                                            }
                                        ) {

                                            Icon(
                                                Icons.Default.Edit,
                                                null,

                                                tint =
                                                    Color(0xFFFF7043)
                                            )
                                        }
                                    }
                                }

                                // 📦 Product Info
                                Column(
                                    modifier = Modifier
                                        .padding(14.dp)
                                        .fillMaxWidth()
                                ) {

                                    Text(
                                        text = product.name,

                                        fontWeight = FontWeight.Bold,

                                        style =
                                            MaterialTheme.typography.titleMedium,

                                        maxLines = 2
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(10.dp)
                                    )

                                    Text(
                                        text = "₹${product.price}",

                                        color =
                                            Color(0xFFFF7043),

                                        fontWeight =
                                            FontWeight.Bold,

                                        style =
                                            MaterialTheme.typography.titleMedium
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(10.dp)
                                    )

                                    AssistChip(
                                        onClick = {},

                                        label = {
                                            Text(product.category)
                                        }
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(16.dp)
                                    )

                                    OutlinedButton(
                                        onClick = {
                                            editingProduct = product
                                        },

                                        modifier =
                                            Modifier.fillMaxWidth(),

                                        shape =
                                            RoundedCornerShape(18.dp)
                                    ) {

                                        Icon(
                                            Icons.Default.Edit,
                                            null
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.width(8.dp)
                                        )

                                        Text("Edit Product")
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
@Composable
fun VendorAboutPage() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFFFF8F5)
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // 🔥 Hero Header
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
                        horizontal = 24.dp,
                        vertical = 36.dp
                    )
            ) {

                Column {

                    Text(
                        text = "Halli Santhe 🌾",

                        style = MaterialTheme.typography.headlineLarge,

                        color = Color.White,

                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text =
                            "Connecting Rural Artisans With Digital Buyers",

                        color = Color.White.copy(alpha = 0.92f),

                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 📊 Quick Stats
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        QuickStatCard(
                            title = "100%",
                            subtitle = "Local"
                        )

                        QuickStatCard(
                            title = "Digital",
                            subtitle = "Marketplace"
                        )

                        QuickStatCard(
                            title = "Rural",
                            subtitle = "Focus"
                        )
                    }
                }
            }

            // 📜 Scroll Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {

                // 🚀 Section Title
                Text(
                    text = "Getting Started",

                    style = MaterialTheme.typography.headlineMedium,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🛍️ Upload Card
                PremiumInfoCard(
                    emoji = "🛍️",
                    title = "Artisan Upload",
                    description =
                        "Upload products & details to showcase local creations widely."
                )

                Spacer(modifier = Modifier.height(18.dp))

                // 🔍 Browse Card
                PremiumInfoCard(
                    emoji = "🔎",
                    title = "Buyer Browse",
                    description =
                        "Customers can explore products in a beautiful grid marketplace experience."
                )

                Spacer(modifier = Modifier.height(18.dp))

                // 🎯 Mission Section
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(28.dp),

                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFFFFF3E0)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {

                        Text(
                            text = "Our Mission",

                            style = MaterialTheme.typography.titleLarge,

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text =
                                "To bridge the gap between talented village artisans and digital consumers through an easy-to-use marketplace platform."
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))


                }
        }
    }
}
@Composable
fun PremiumInfoCard(
    emoji: String,
    title: String,
    description: String
) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        elevation = CardDefaults.elevatedCardElevation(8.dp),

        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                text = "$emoji  $title",

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,

                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun FeatureItem(text: String) {

    Column {

        Text(
            text = text,

            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun QuickStatCard(
    title: String,
    subtitle: String
) {

    Card(
        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.22f)
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,

                color = Color.White,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,

                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
@Composable
fun DashboardStatCard(
    title: String,
    subtitle: String
) {

    Card(
        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                Color.White.copy(alpha = 0.22f)
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = title,

                color = Color.White,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,

                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}