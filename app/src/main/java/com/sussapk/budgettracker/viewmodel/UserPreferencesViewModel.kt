package com.sussapk.budgettracker.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sussapk.budgettracker.data.datastore.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserPreferencesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val preferences =
        UserPreferences(application)

    val userName =
        preferences.userName.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    val initialBalance =
        preferences.initialBalance.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    val isFirstLaunch =
        preferences.isFirstLaunch.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            true
        )

    fun saveUserName(
        name: String
    ) {

        viewModelScope.launch {

            preferences.saveUserName(name)

        }

    }

    fun saveInitialBalance(
        balance: Double
    ) {

        viewModelScope.launch {

            preferences.saveInitialBalance(balance)

        }

    }

    fun completeFirstLaunch() {

        viewModelScope.launch {

            preferences.setFirstLaunchCompleted()

        }

    }

    fun updateUserName(
        name: String
    ) {
        viewModelScope.launch {
            preferences.updateUserName(name)
        }
    }

    fun updateInitialBalance(
        balance: Double
    ) {
        viewModelScope.launch {
            preferences.updateInitialBalance(balance)
        }
    }

}