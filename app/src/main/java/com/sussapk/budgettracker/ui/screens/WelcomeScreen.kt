package com.sussapk.budgettracker.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sussapk.budgettracker.ui.navigation.Screen
import android.app.Application
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModel
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModelFactory

@Composable
fun WelcomeScreen(
    navController: NavHostController
) {

    var username by remember {

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
            "Welcome",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(

            value = username,

            onValueChange = {

                username = it

            },

            modifier = Modifier.fillMaxWidth(),

            label = {

                Text("Your Name")

            }

        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(

            modifier = Modifier.fillMaxWidth(),

            enabled = username.isNotBlank(),

            onClick = {

                viewModel.saveUserName(username.trim())
                navController.navigate(
                    Screen.InitialBalance.route
                )

            }

        ) {

            Text("Next")

        }

    }

}