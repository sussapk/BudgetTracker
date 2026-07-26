package com.sussapk.budgettracker.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sussapk.budgettracker.ui.screens.AddCashEntryScreen
import com.sussapk.budgettracker.ui.screens.HomeScreen
import com.sussapk.budgettracker.ui.screens.InitialBalanceScreen
import com.sussapk.budgettracker.ui.screens.MoreOptionsScreen
import com.sussapk.budgettracker.ui.screens.SplashScreen
import com.sussapk.budgettracker.ui.screens.TransactionHistoryScreen
import com.sussapk.budgettracker.ui.screens.WelcomeScreen

@Composable
fun NavGraph(
    openAddCashEntry: Boolean = false
){

    val navController = rememberNavController()
    LaunchedEffect(openAddCashEntry) {

        if (openAddCashEntry) {

            navController.navigate("add_cash_entry")

        }

    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.TransactionHistory.route) {
            TransactionHistoryScreen(navController)
        }

        composable(Screen.MoreOptions.route) {
            MoreOptionsScreen(navController)
        }

        composable(
            route = "add_cash_entry?transactionId={transactionId}"
        ) { backStackEntry ->

            val transactionId =
                backStackEntry.arguments
                    ?.getString("transactionId")
                    ?.toIntOrNull()

            AddCashEntryScreen(
                navController = navController,
                transactionId = transactionId
            )
        }

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Screen.InitialBalance.route) {
            InitialBalanceScreen(navController)
        }

    }


}

