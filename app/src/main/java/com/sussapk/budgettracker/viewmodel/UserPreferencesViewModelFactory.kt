package com.sussapk.budgettracker.viewmodel


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserPreferencesViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                UserPreferencesViewModel::class.java
            )
        ) {

            return UserPreferencesViewModel(
                application
            ) as T

        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )

    }

}