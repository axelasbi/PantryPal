package com.example.pantrypal.ui.additem

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddItemScreen(
    onAddItem: (
        String,
        Int,
        String,
        Long
    ) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var expirationDays by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Add Item",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = expirationDays,
            onValueChange = {
                expirationDays = it
            },
            label = {
                Text("Days Until Expiration")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val days =
                    expirationDays.toLongOrNull() ?: 0

                val expirationDate =
                    System.currentTimeMillis() +
                            (days * 24 * 60 * 60 * 1000)

                onAddItem(
                    name,
                    quantity.toIntOrNull() ?: 0,
                    category,
                    expirationDate
                )
            }
        ) {
            Text("Save")
        }
    }
}