package com.sussapk.budgettracker.ui.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sussapk.budgettracker.ui.navigation.Screen
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModel
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModelFactory
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavHostController
) {

    val viewModel: UserPreferencesViewModel = viewModel(
        factory = UserPreferencesViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val isFirstLaunch by viewModel.isFirstLaunch.collectAsState()

    LaunchedEffect(isFirstLaunch) {

        delay(1200)


        if (isFirstLaunch) {

            navController.navigate(Screen.Welcome.route) {

                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }

            }

        } else {

            navController.navigate(Screen.Home.route) {

                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }

            }

        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        //contentAlignment = Alignment.Center
    ) {/*

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Budget Tracker",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

        }
*/
    }

}