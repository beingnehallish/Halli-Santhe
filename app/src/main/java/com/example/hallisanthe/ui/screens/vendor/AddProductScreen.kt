package com.example.hallisanthe.ui.screens.vendor

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hallisanthe.data.AppDatabase
import com.example.hallisanthe.data.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    db: AppDatabase,
    existingProduct: Product? = null,
    onDone: () -> Unit
) {

    val dao = db.productDao()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var name by remember {
        mutableStateOf(existingProduct?.name ?: "")
    }

    var price by remember {
        mutableStateOf(existingProduct?.price ?: "")
    }

    val categories = listOf(
        "Handicrafts",
        "Pottery",
        "Textiles",
        "Jewelry",
        "Toys",
        "Home Decor"
    )

    var selectedCategory by remember {
        mutableStateOf(existingProduct?.category ?: categories[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf(
            existingProduct?.imageUri?.let {
                Uri.parse(it)
            }
        )
    }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        uri?.let {

            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            imageUri = it
        }
    }
    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Product?")
            },

            text = {
                Text(
                    "Are you sure you want to permanently delete this product?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        showDeleteDialog = false

                        scope.launch {

                            existingProduct?.let {

                                dao.deleteProduct(it)

                                Toast.makeText(
                                    context,
                                    "Product Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onDone()
                            }
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {

                    Text("Yes")
                }
            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {

                    Text("Cancel")
                }
            }
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
                .padding(24.dp)
        ) {

            Column {

                Text(
                    text =
                        if (existingProduct == null)
                            "Upload Product 📦"
                        else
                            "Edit Product ✏️",

                    style = MaterialTheme.typography.headlineMedium,

                    color = Color.White,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Showcase your artisan creations digitally",

                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        // 📋 Scrollable Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // 🏷️ Product Name
            OutlinedTextField(
                value = name,

                onValueChange = {
                    name = it
                },

                label = {
                    Text("Product Name")
                },

                leadingIcon = {
                    Icon(Icons.Default.Inventory, null)
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                singleLine = true
            )

            // 💰 Price
            OutlinedTextField(
                value = price,

                onValueChange = {
                    price = it
                },

                label = {
                    Text("Price")
                },

                leadingIcon = {
                    Icon(Icons.Default.CurrencyRupee, null)
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                singleLine = true
            )

            // 🧩 Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,

                onExpandedChange = {
                    expanded = !expanded
                }
            ) {

                OutlinedTextField(
                    value = selectedCategory,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Category")
                    },

                    leadingIcon = {
                        Icon(Icons.Default.Category, null)
                    },

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,

                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    categories.forEach { category ->

                        DropdownMenuItem(
                            text = {
                                Text(category)
                            },

                            onClick = {

                                selectedCategory = category

                                expanded = false
                            }
                        )
                    }
                }
            }

            // 📷 Image Upload Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                elevation = CardDefaults.elevatedCardElevation(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (imageUri != null) {

                        AsyncImage(
                            model = imageUri,

                            contentDescription = null,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(20.dp)),

                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Button(
                        onClick = {
                            launcher.launch(arrayOf("image/*"))
                        },

                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Icon(
                            Icons.Default.Image,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            if (imageUri == null)
                                "Upload Product Image"
                            else
                                "Change Product Image"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🚀 Upload / Update Button
            Button(
                onClick = {

                    if (
                        name.isBlank() ||
                        price.isBlank() ||
                        imageUri == null
                    ) {

                        Toast.makeText(
                            context,
                            "Fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    scope.launch {

                        if (existingProduct == null) {

                            dao.insertProduct(
                                Product(
                                    name = name,
                                    price = price,
                                    category = selectedCategory,
                                    imageUri = imageUri.toString()
                                )
                            )

                        } else {

                            dao.updateProduct(
                                existingProduct.copy(
                                    name = name,
                                    price = price,
                                    category = selectedCategory,
                                    imageUri = imageUri.toString()
                                )
                            )
                        }

                        Toast.makeText(
                            context,

                            if (existingProduct == null)
                                "Product Added Successfully"
                            else
                                "Product Updated Successfully",

                            Toast.LENGTH_SHORT
                        ).show()

                        onDone()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                shape = RoundedCornerShape(22.dp)
            ) {

                Text(
                    text =
                        if (existingProduct == null)
                            "Upload Product"
                        else
                            "Save Changes",

                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (existingProduct != null) {

                OutlinedButton(

                    onClick = {
                        showDeleteDialog = true
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(20.dp),

                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red
                    )
                ) {

                    Text(
                        text = "Delete Product",

                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}