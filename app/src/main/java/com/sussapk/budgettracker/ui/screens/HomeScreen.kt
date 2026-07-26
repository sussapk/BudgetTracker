package com.sussapk.budgettracker.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sussapk.budgettracker.ui.components.BalanceCard
import com.sussapk.budgettracker.ui.components.RecentTransactionHeader
import com.sussapk.budgettracker.ui.components.TopGreeting
import com.sussapk.budgettracker.ui.components.TransactionCard
import com.sussapk.budgettracker.ui.navigation.Screen
import com.sussapk.budgettracker.viewmodel.CashTransactionViewModel
import com.sussapk.budgettracker.viewmodel.CashTransactionViewModelFactory
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModel
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModelFactory

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    val viewModel: CashTransactionViewModel = viewModel(
        factory = CashTransactionViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val userPreferencesViewModel: UserPreferencesViewModel = viewModel(
        factory = UserPreferencesViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val transactions by viewModel.allTransactions.collectAsState()

    val username by userPreferencesViewModel.userName.collectAsState()

    val initialBalance by userPreferencesViewModel.initialBalance.collectAsState()

    val totalIncome = transactions
        .filter { it.type == "Income" }
        .sumOf { it.amount }

    val totalExpense = transactions
        .filter { it.type == "Expense" }
        .sumOf { it.amount }

    val currentBalance = initialBalance + totalIncome - totalExpense

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddCashEntry.route)
                },
                modifier = Modifier.fillMaxWidth(0.9f)
                    .height(60.dp),
                shape = RoundedCornerShape(26.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row{
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Add Cash Transaction")
                }


            }

        },
        floatingActionButtonPosition = FabPosition.Center


    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentPadding = PaddingValues(bottom = 90.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            item {

                TopGreeting(

                    username = username,

                    onMoreClick = {
                        navController.navigate(Screen.MoreOptions.route)
                    }

                )

            }

            item {

                BalanceCard(

                    balance = currentBalance ,

                    totalIncome = totalIncome,

                    totalExpense = totalExpense,

                    containerColor =MaterialTheme.colorScheme.surface

                )

            }

            /*item {

                NotificationStatusCard(

                    enabled = false,

                    onEnableClick = {

                    }

                )

            }*/

            if (transactions.isEmpty()) {

                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(

                            horizontalAlignment = Alignment.CenterHorizontally

                        ){


                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary

                            )

                            Spacer(modifier = Modifier.height(26.dp))

                            Text(
                                text = "No Recorded Cash",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )



                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Tap the '+' button to add your first record",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )

                        }


                    }

                }

            } else {

                item {

                    RecentTransactionHeader(
                        onSeeAllClick = {
                            navController.navigate(Screen.TransactionHistory.route)
                        }
                    )

                }

                items(transactions.take(3)) { transaction ->

                    TransactionCard(
                        amount = transaction.amount,
                        category = transaction.category,
                        date = transaction.date,
                        time = transaction.time,
                        type = transaction.type,
                        onEditClick = {

                            navController.navigate(
                                "add_cash_entry?transactionId=${transaction.id}"
                            )

                        },
                        onDeleteClick = {
                            viewModel.delete(transaction)

                        }
                    )

                }

            }



        }

    }

}