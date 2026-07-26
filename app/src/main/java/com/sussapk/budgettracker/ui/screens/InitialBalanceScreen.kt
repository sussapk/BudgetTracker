package com.sussapk.budgettracker.ui.screens


import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sussapk.budgettracker.ui.navigation.Screen
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModel
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModelFactory

@Composable
fun InitialBalanceScreen(
    navController: NavHostController
) {

    var balance by remember {

        mutableStateOf("")

    }

    val viewModel: UserPreferencesViewModel = viewModel(
        factory = UserPreferencesViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Text(
            "Initial Balance",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(

            value = balance,

            onValueChange = {

                balance = it

            },

            modifier = Modifier.fillMaxWidth(),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),

            label = {

                Text("Balance")

            }

        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            OutlinedButton(

                modifier = Modifier.weight(1f),

                onClick = {

                    viewModel.saveInitialBalance(0.0)

                    viewModel.completeFirstLaunch()

                    navController.navigate(Screen.Home.route) {

                        popUpTo(0)

                    }

                }

            ) {

                Text("Skip")

            }

            Button(

                modifier = Modifier.weight(1f),

                onClick = {

                    val amount = balance.trim().toDoubleOrNull() ?: 0.0
                    viewModel.saveInitialBalance(amount)

                    viewModel.completeFirstLaunch()

                    navController.navigate(Screen.Home.route) {

                        popUpTo(0)

                    }

                }

            ) {

                Text("Finish")

            }

        }

    }

}