package com.sussapk.budgettracker.ui.navigation

sealed class Screen(val route: String) {


    data object Splash : Screen("splash")

    data object Welcome : Screen("welcome")

    data object InitialBalance : Screen("initial_balance")

    data object Home : Screen("home")

    data object TransactionHistory : Screen("transaction_history")

    data object MoreOptions : Screen("more_options")

    object AddCashEntry : Screen("add_cash_entry?transactionId={transactionId}")
}