package com.sussapk.budgettracker.data.datastore


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferences(
    private val context: Context
) {

    companion object {

        val USER_NAME = stringPreferencesKey("user_name")

        val INITIAL_BALANCE = doublePreferencesKey("initial_balance")

        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")

    }

    suspend fun saveUserName(
        name: String
    ) {

        context.dataStore.edit {

            it[USER_NAME] = name

        }

    }

    suspend fun saveInitialBalance(
        balance: Double
    ) {

        context.dataStore.edit {

            it[INITIAL_BALANCE] = balance

        }

    }

    suspend fun setFirstLaunchCompleted() {

        context.dataStore.edit {

            it[FIRST_LAUNCH] = false

        }

    }

    val userName: Flow<String> =
        context.dataStore.data.map {

            it[USER_NAME] ?: ""

        }

    val initialBalance: Flow<Double> =
        context.dataStore.data.map {

            it[INITIAL_BALANCE] ?: 0.0

        }

    val isFirstLaunch: Flow<Boolean> =
        context.dataStore.data.map {

            it[FIRST_LAUNCH] ?: true

        }

    suspend fun updateUserName(
        name: String
    ) {
        context.dataStore.edit {
            it[USER_NAME] = name
        }
    }

    suspend fun updateInitialBalance(
        balance: Double
    ) {
        context.dataStore.edit {
            it[INITIAL_BALANCE] = balance
        }
    }

}