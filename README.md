# 🌾 Halli-Santhe Digital (Retail)

## 📌 Project Overview

Halli-Santhe Digital is a **Hyper-Local Marketplace Android Application** developed using **Jetpack Compose + Room Database**.  
The application helps village artisans showcase and sell their handmade products digitally while allowing customers to browse and contact sellers easily.

This project promotes the concept of **"Vocal for Local"** by connecting local artisans directly with buyers through a modern mobile platform.



# 🎯 Problem Statement

Local artisans often have limited reach because they sell products only in physical weekly markets.  
Customers are usually unaware of available products unless they travel to those markets.

Halli-Santhe Digital solves this problem by creating a digital catalog marketplace where artisan products can be discovered easily.


# Screenshots
<img width="1920" height="1080" alt="Untitled design (8)" src="https://github.com/user-attachments/assets/d36f1993-e44c-461c-8279-f88e05f4fb4c" />

# ✨ Features

## 👨‍🌾 Vendor Features

- Vendor Login & Signup
- Add New Products
- Upload Product Image
- Edit Existing Products
- Delete Products with Confirmation Popup
- Search Products
- View Uploaded Products
- Category Selection
- Vendor Profile Page
- Change Password
- Logout Functionality



## 🛍️ Customer Features

- Customer Login & Signup
- Browse Products in Grid Layout
- Search Products
- Sort Products (Price Low → High / High → Low)
- Filter Products by Category
- View Detailed Product Page
- Contact Seller UI
- Dummy Chat System
- Auto Smart Replies
- Customer Profile Page
- Change Password
- Logout Functionality



# 💬 Smart Chat Simulation

The project contains a simulated chat system between customer and seller.

### Example Auto Responses

| Customer Message | Vendor Auto Reply |
|---|---|
| "stock" | "Yes, we are currently in stock 😊" |
| "bulk" | "I need to check bulk availability." |
| "payment" | "The QR code will be sent via SMS soon." |
| "delivery" | "Delivery usually takes 3-5 business days 🚚" |

This gives the application an AI-like interactive experience without requiring a real backend chat server.

---

# 🧑‍💻 Tech Stack

| Technology | Usage |
|---|---|
| Kotlin | Programming Language |
| Jetpack Compose | UI Development |
| Room Database | Local Data Storage |
| Material 3 | Modern UI Components |
| Coil | Image Loading |
| Android Studio | Development Environment |

---

# 🗂️ Project Structure

```bash
com.example.hallisanthe
│
├── data
│   ├── AppDatabase.kt
│   ├── Product.kt
│   ├── ProductDao.kt
│   ├── User.kt
│   └── UserDao.kt
│
├── navigation
│   └── AppNavigation.kt
│
├── ui
│   ├── screens
│   │   ├── customer
│   │   ├── vendor
│   │   ├── AuthScreen.kt
│   │   ├── SplashScreen.kt
│   │   └── RoleSelectionScreen.kt
│   │
│   └── theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── MainActivity.kt
