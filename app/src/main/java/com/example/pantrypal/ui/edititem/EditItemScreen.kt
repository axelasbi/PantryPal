package com.example.pantrypal.ui.edititem

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pantrypal.data.entity.PantryItem

@Composable
fun EditItemScreen(
    item: PantryItem,
    onSave: (PantryItem) -> Unit
) {

    var name by remember {
        mutableStateOf(item.itemName)
    }

    var quantity by remember {
        mutableStateOf(item.quantity.toString())
    }

    var category by remember {
        mutableStateOf(item.category)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Edit Item",
            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Item Name")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                quantity = it
            },
            label = {
                Text("Quantity")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
            },
            label = {
                Text("Category")
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                onSave(
                    item.copy(
                        itemName = name,
                        quantity =
                            quantity.toIntOrNull()
                                ?: item.quantity,
                        category = category
                    )
                )
            }
        ) {
            Text("Save Changes")
        }
    }
}