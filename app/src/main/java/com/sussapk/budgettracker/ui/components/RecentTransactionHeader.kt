package com.sussapk.budgettracker.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecentTransactionHeader(
    onSeeAllClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
             .padding(horizontal = 20.dp)
    ) {

        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        TextButton(
            onClick = onSeeAllClick
        ) {

            Text("See All")

        }

    }

}