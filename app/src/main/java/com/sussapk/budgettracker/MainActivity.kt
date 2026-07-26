package com.sussapk.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sussapk.budgettracker.ui.navigation.NavGraph
import com.sussapk.budgettracker.ui.theme.BudgetTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val openAddCashEntry =
            intent?.getBooleanExtra("openAddCashEntry", false) ?: false

        setContent {

            BudgetTrackerTheme {

                NavGraph(
                    openAddCashEntry = openAddCashEntry
                )

            }

        }

    }

}
