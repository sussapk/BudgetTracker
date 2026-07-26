package com.sussapk.budgettracker.ui.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sussapk.budgettracker.data.room_database.CashTransaction
import com.sussapk.budgettracker.ui.theme.ExpenseLightRed
import com.sussapk.budgettracker.ui.theme.ExpenseRed
import com.sussapk.budgettracker.ui.theme.IncomeGreen
import com.sussapk.budgettracker.ui.theme.IncomeLightGreen
import com.sussapk.budgettracker.viewmodel.CashTransactionViewModel
import com.sussapk.budgettracker.viewmodel.CashTransactionViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddCashEntryScreen(
    navController: NavHostController,
    transactionId: Int? = null
) {

    var isExpense by remember { mutableStateOf(true) }

    var amount by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("") }

    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val viewModel: CashTransactionViewModel = viewModel(
        factory = CashTransactionViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val isFormValid = amount.toDoubleOrNull() != null && category.isNotBlank()


    LaunchedEffect(transactionId) {

        if (transactionId != null) {

            val transaction = viewModel.getTransactionById(transactionId)

            transaction?.let {

                amount = it.amount.toString()
                category = it.category
                isExpense = it.type == "Expense"

                date = it.date
                time = it.time
            }

        }

    }

    val expenseSuggestions = listOf(
        "Food",
        "Fare",
        "Shopping",
        "Entertainment",
        "Bills",
        "Medicine",
        "Rent",
        "Travel"
    )

    val incomeSuggestions = listOf(
        "Salary",
        "Freelance",
        "Bonus",
        "Sales",
        "Interest",
        "Gift",
        "Refund",
        "Other"
    )

    val suggestions =
        if (isExpense) expenseSuggestions
        else incomeSuggestions


    Scaffold(

    ) {
            paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentPadding = PaddingValues(
                start = 20.dp,
                top = 90.dp,
                end = 20.dp,
            ),

            verticalArrangement = Arrangement.spacedBy(18.dp)

        )
        {



                item {

                    Text(
                        text = if (transactionId == null)
                            "New Transaction"

                        else
                            "Edit Transaction",

                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold

                    )


                }

                item {

                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        SegmentedButton(
                            selected = isExpense,
                            onClick = { isExpense = true },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 0,
                                count = 2
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = ExpenseLightRed,
                                activeContentColor = ExpenseRed,
                                inactiveContainerColor = Color.Transparent.copy(0.01f),
                                inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            icon = {}


                        )
                        {
                            Text(
                                text = "Expenses",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        SegmentedButton(
                            selected = !isExpense,
                            onClick = { isExpense = false },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 1,
                                count = 2
                            ),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = IncomeLightGreen,
                                activeContentColor = IncomeGreen,
                                inactiveContainerColor = Color.Transparent.copy(0.01f),
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

                    }

                }

                item {

                    OutlinedTextField(

                        value = amount,

                        onValueChange = {
                            amount = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Amount")
                        },

                        prefix = {
                            Text(
                                text = "Rs ",
                                color = if (isExpense) ExpenseRed else IncomeGreen,
                                fontWeight = FontWeight.Bold
                            )
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )

                    )

                }

                item {

                    OutlinedTextField(

                        value = category,

                        onValueChange = {
                            category = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Source / Category")
                        }

                    )

                }

                item {

                    Text(
                        text = "Quick Suggestions",
                        style = MaterialTheme.typography.titleMedium
                    )

                }

                item {

                    FlowRow(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.spacedBy(8.dp),

                        verticalArrangement = Arrangement.spacedBy(8.dp)

                    ) {

                        suggestions.forEach {

                            FilterChip(

                                selected = false,

                                onClick = {
                                    category = it
                                },

                                label = {
                                    Text(it)
                                }

                            )

                        }

                    }

                }

                item {

                    Spacer(modifier = Modifier.height(10.dp))

                    Card ( colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(4.dp),

                        ){

                        androidx.compose.foundation.layout.Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),


                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {

                            OutlinedButton(

                                onClick = {

                                    navController.popBackStack()

                                },

                                shape = RoundedCornerShape(10.dp)

                            ) {

                                Text("Cancel")

                            }

                            OutlinedButton(
                                onClick = {

                                    if (!isFormValid) return@OutlinedButton

                                    val amountValue = amount.toDoubleOrNull() ?: return@OutlinedButton

                                    val currentDate = SimpleDateFormat(
                                        "dd MMM yyyy",
                                        Locale.getDefault()
                                    ).format(Date())

                                    val currentTime = SimpleDateFormat(
                                        "hh:mm a",
                                        Locale.getDefault()
                                    ).format(Date())

                                    val transaction = CashTransaction(
                                        id = transactionId ?: 0,
                                        amount = amountValue,
                                        type = if (isExpense) "Expense" else "Income",
                                        category = category,

                                        date = if (transactionId == null) currentDate else date,
                                        time = if (transactionId == null) currentTime else time
                                    )


                                    if (transactionId == null) {
                                        viewModel.insert(transaction)
                                    } else {
                                        viewModel.update(transaction)
                                    }

                                    navController.popBackStack()
                                },

                                enabled = isFormValid,

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                    disabledContainerColor = Color.Transparent.copy(0.1f),
                                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),

                                shape = RoundedCornerShape(10.dp),


                                ) {
                                Text(
                                    if (transactionId == null)
                                        "Add Transaction"
                                    else
                                        "Update Transaction"
                                )
                            }




                        }

                    }

                }

            }

        }
    }



