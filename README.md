## 1. App Overview
   PantryPal is an Android mobile application designed to help users manage household food inventory efficiently. The application allows users to track pantry items, monitor expiration dates, and organize products through a simple and intuitive interface.
   Users can create an account, securely log in using Firebase Authentication, and manage their personal pantry inventory stored in Cloud Firestore. The application supports full CRUD (Create, Read, Update, Delete) operations, enabling users to add new items, edit existing records, and remove items that are no longer needed.
   To improve inventory management, PantryPal includes features such as expiration date tracking, an "Expiring Soon" section, search functionality, category filtering, sorting options, and a dashboard that provides useful inventory statistics and insights.
   ### Key Features
   - User registration and login with Firebase Authentication
   - Cloud-based pantry storage using Firebase Firestore
   - Add, edit, and delete pantry items
   - Expiration date tracking and alerts
   - Search pantry items by name
   - Filter items by category
   - Sort items by name, quantity, or expiration date
   - Dashboard with inventory analytics and summaries
   - User-specific pantry data isolation
   ### Technologies Used
   - Kotlin
   - Android Studio
   - Jetpack Compose
   - MVVM Architecture
   - Firebase Authentication
   - Cloud Firestore
## 2. Data Model
   PantryPal uses Firebase Authentication for user management and Cloud Firestore for storing pantry data. The application consists of two primary data entities: User and Pantry Item.  
   ### User
   - uid
   - email
   ### Pantry Item
   - itemId
   - userId
   - itemName
   - quantity
   - category
   - expirationDate
## 3. Pre-requisites
   - Android Studio (Panda or newer)
   - Android SDK 36+
   - Kotlin 2.x
   - JDK 17 or newer
   - Android Emulator or physical Android device
   ### 3a. Dependencies
   - `libs.androidx.compose.material3`
   - `androidx.navigation:navigation-compose:2.9.0`
   - `androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1`
   - `libs.androidx.activity.compose`
   - `com.google.firebase:firebase-auth`
   - `com.google.firebase:firebase-firestore`
   ### 3b. Plugins
   - `libs.plugins.android.application`
   - `libs.plugins.kotlin.compose`
   - `com.google.gms.google-services`
