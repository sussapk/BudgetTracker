package com.sussapk.budgettracker.ui.screens

 import android.app.Application
 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.PaddingValues
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.lazy.items
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.ArrowBack
 import androidx.compose.material.icons.filled.Search
 import androidx.compose.material.icons.filled.SearchOff
 import androidx.compose.material.icons.filled.TrendingDown
 import androidx.compose.material.icons.filled.TrendingUp
 import androidx.compose.material3.CenterAlignedTopAppBar
 import androidx.compose.material3.ExperimentalMaterial3Api
 import androidx.compose.material3.Icon
 import androidx.compose.material3.IconButton
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.OutlinedTextField
 import androidx.compose.material3.OutlinedTextFieldDefaults
 import androidx.compose.material3.Scaffold
 import androidx.compose.material3.SegmentedButton
 import androidx.compose.material3.SegmentedButtonDefaults
 import androidx.compose.material3.SingleChoiceSegmentedButtonRow
 import androidx.compose.material3.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.collectAsState
 import androidx.compose.runtime.getValue
 import androidx.compose.runtime.mutableStateOf
 import androidx.compose.runtime.remember
 import androidx.compose.runtime.setValue
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.platform.LocalContext
 import androidx.compose.ui.text.font.FontWeight
 import androidx.compose.ui.unit.dp
 import androidx.lifecycle.viewmodel.compose.viewModel
 import androidx.navigation.NavHostController
 import com.sussapk.budgettracker.ui.components.NoTransactionFoundCard
 import com.sussapk.budgettracker.ui.components.TransactionCard
 import com.sussapk.budgettracker.ui.theme.ExpenseLightRed
 import com.sussapk.budgettracker.ui.theme.ExpenseRed
 import com.sussapk.budgettracker.ui.theme.IncomeGreen
 import com.sussapk.budgettracker.ui.theme.IncomeLightGreen
 import com.sussapk.budgettracker.viewmodel.CashTransactionViewModel
 import com.sussapk.budgettracker.viewmodel.CashTransactionViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    navController: NavHostController
) {

    val viewModel: CashTransactionViewModel = viewModel(
        factory = CashTransactionViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val transactions by viewModel.allTransactions.collectAsState()

    var selectedFilter by remember {

        mutableStateOf("All")

    }

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredTransactions = transactions
        .filter {

            when (selectedFilter) {

                "Income" -> it.type == "Income"

                "Expense" -> it.type == "Expense"

                else -> true
            }

        }
        .filter {

            searchText.isBlank() ||

                    it.category.contains(searchText, ignoreCase = true) ||

                    it.amount.toString().contains(searchText) ||

                    it.date.contains(searchText, ignoreCase = true)

        }

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(
                        text = "Transaction History",
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )

                    }

                }

            )

        }

    ) { paddingValues ->




        LazyColumn(

            modifier = Modifier
                .fillMaxSize(),

            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = 16.dp
            ),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            item {

                SingleChoiceSegmentedButtonRow(    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {

                    SegmentedButton(
                        modifier = Modifier.weight(1f),


                        selected = selectedFilter == "All",

                        onClick = {
                            selectedFilter = "All"
                        },

                        shape = SegmentedButtonDefaults.itemShape(
                            index = 0,
                            count = 3
                        ),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = MaterialTheme.colorScheme.primary,
                            activeContentColor = MaterialTheme.colorScheme.onPrimary,
                            inactiveContainerColor = Color.Transparent,
                            inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        icon = {}

                    ) {

                        Text(
                            text = "All",
                            fontWeight = FontWeight.Bold
                        )

                    }

                    SegmentedButton(

                        selected = selectedFilter == "Income",

                        onClick = {
                            selectedFilter = "Income"
                        },

                        shape = SegmentedButtonDefaults.itemShape(
                            index = 1,
                            count = 3
                        ),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = IncomeLightGreen,
                            activeContentColor = IncomeGreen,
                            inactiveContainerColor = Color.Transparent,
                            inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        icon = {}
                    )
                    {

                        Text(
                            text = "Income",
                            fontWeight = FontWeight.Bold
                        )

                    }

                    SegmentedButton(

                        selected = selectedFilter == "Expense",

                        onClick = {
                            selectedFilter = "Expense"
                        },

                        shape = SegmentedButtonDefaults.itemShape(
                            index = 2,
                            count = 3
                        ),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = ExpenseLightRed,
                            activeContentColor = ExpenseRed,
                            inactiveContainerColor = Color.Transparent,
                            inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        icon = {}


                    ) {

                        Text(
                            text = "Expenses",
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

            item {

                OutlinedTextField(

                    value = searchText,

                    onValueChange = {
                        searchText = it
                    },

                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),

                    placeholder = {
                        Text("Search by category, amount or date")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )

                    },

                    singleLine = true,

                    shape = RoundedCornerShape(12.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary
                    )
                )

            }

            item {

                Text(
                    text = "${filteredTransactions.size} transaction(s)",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

            if (filteredTransactions.isEmpty()) {

                item {

                    when {

                        searchText.isNotBlank() -> {

                            NoTransactionFoundCard(
                                icon = Icons.Default.SearchOff,
                                title = "No Matching Transactions",
                                subtitle = "Try searching by category, amount or date."
                            )

                        }

                        selectedFilter == "Income" -> {

                            NoTransactionFoundCard(
                                icon = Icons.Default.TrendingUp,
                                title = "No Income Transactions",
                                subtitle = "Your income records will appear here."
                            )

                        }

                        selectedFilter == "Expense" -> {

                            NoTransactionFoundCard(
                                icon = Icons.Default.TrendingDown,
                                title = "No Expense Transactions",
                                subtitle = "Your expense records will appear here."
                            )

                        }

                    }

                }

            } else {

                items(filteredTransactions) { transaction ->

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