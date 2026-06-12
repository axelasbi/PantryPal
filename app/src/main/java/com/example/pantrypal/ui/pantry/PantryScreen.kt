package com.example.pantrypal.ui.pantry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.pantrypal.data.entity.PantryItem
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryScreen(
    items: List<PantryItem>,
    onAddClick: () -> Unit,
    onDeleteClick: (PantryItem) -> Unit,
    onEditClick: (PantryItem) -> Unit,
    onDashboardClick: () -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedSort by remember {
        mutableStateOf("Name")
    }

    var sortExpanded by remember {
        mutableStateOf(false)
    }

    val categories = listOf(
        "All"
    ) + items
        .map { it.category }
        .distinct()
        .sorted()

    val filteredItems = items.filter { item ->

        val matchesSearch =

            item.itemName.contains(
                searchText,
                ignoreCase = true
            )

        val matchesCategory =

            selectedCategory == "All" ||

                    item.category ==
                    selectedCategory

        matchesSearch &&
                matchesCategory
    }

    val showNoResults =

        searchText.isNotBlank() &&

                filteredItems.isEmpty()

    val expiringSoonItems = items.filter { item ->

        val daysRemaining =
            ceil(
                (
                        item.expirationDate -
                                System.currentTimeMillis()
                        ).toDouble() /
                        (24 * 60 * 60 * 1000)
            ).toInt()

        daysRemaining in 0..3
    }

    val sortOptions = listOf(
        "Name",
        "Quantity",
        "Expiration"
    )

    val displayedItems =
        when (selectedSort) {

            "Quantity" ->
                filteredItems.sortedByDescending {
                    it.quantity
                }

            "Expiration" ->
                filteredItems.sortedBy {
                    it.expirationDate
                }

            else ->
                filteredItems.sortedBy {
                    it.itemName
                }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "My Pantry",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = searchText,

            onValueChange = {
                searchText = it
            },

            label = {
                Text("Search Items")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

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

                modifier =
                    Modifier
                        .menuAnchor()
                        .fillMaxWidth()
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

                            selectedCategory =
                                category

                            expanded = false
                        }
                    )
                }
            }


        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ExposedDropdownMenuBox(
            expanded = sortExpanded,
            onExpandedChange = {
                sortExpanded = !sortExpanded
            }
        ) {

            OutlinedTextField(
                value = selectedSort,
                onValueChange = {},
                readOnly = true,

                label = {
                    Text("Sort By")
                },

                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = sortExpanded,
                onDismissRequest = {
                    sortExpanded = false
                }
            ) {

                sortOptions.forEach { option ->

                    DropdownMenuItem(
                        text = {
                            Text(option)
                        },

                        onClick = {
                            selectedSort = option
                            sortExpanded = false
                        }
                    )
                }
            }
        }

        if (expiringSoonItems.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "⚠ Expiring Soon",
                        style =
                            MaterialTheme.typography.titleMedium
                    )

                    expiringSoonItems.forEach { item ->

                        val daysRemaining =
                            ceil(
                                (
                                        item.expirationDate -
                                                System.currentTimeMillis()
                                        ).toDouble() /
                                        (24 * 60 * 60 * 1000)
                            ).toInt()

                        Text(
                            when {
                                daysRemaining <= 0 ->
                                    "${item.itemName} - Expired"

                                daysRemaining == 1 ->
                                    "${item.itemName} - 1 day left"

                                else ->
                                    "${item.itemName} - $daysRemaining days left"
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = onDashboardClick
        ) {
            Text("Dashboard")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddClick
        ) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showNoResults) {

            Text(
                text = "No items found"
            )
        }

        LazyColumn {

            items(displayedItems) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(item.itemName)

                        Text(
                            "Qty: ${item.quantity}"
                        )

                        Text(
                            item.category
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        val daysRemaining =
                            ceil(
                                (
                                        item.expirationDate -
                                                System.currentTimeMillis()
                                        ).toDouble() /
                                        (24 * 60 * 60 * 1000)
                            ).toInt()

                        Text(
                            when {
                                daysRemaining <= 0 ->
                                    "Expired"

                                daysRemaining == 1 ->
                                    "Expires in 1 day"

                                else ->
                                    "Expires in $daysRemaining days"
                            }
                        )

                        Button(
                            onClick = {
                                onEditClick(item)
                            }
                        ) {
                            Text("Edit")
                        }

                        Button(
                            onClick = {
                                onDeleteClick(item)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}