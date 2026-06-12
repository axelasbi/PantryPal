package com.example.pantrypal.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    totalItems: Int,
    totalQuantity: Int,
    expiringSoon: Int,
    expiredItems: Int,
    categories: Int,
    topCategory: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Dashboard",
            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        DashboardCard(
            title = "Total Items",
            value = totalItems.toString()
        )

        DashboardCard(
            title = "Total Quantity",
            value = totalQuantity.toString()
        )

        DashboardCard(
            title = "Categories",
            value = categories.toString()
        )

        DashboardCard(
            title = "Top Category",
            value = topCategory
        )

        DashboardCard(
            title = "Expiring Soon",
            value = expiringSoon.toString()
        )

        DashboardCard(
            title = "Expired Items",
            value = expiredItems.toString()
        )
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style =
                    MaterialTheme.typography.titleMedium
            )

            Text(
                text = value,
                style =
                    MaterialTheme.typography.headlineSmall
            )
        }
    }
}