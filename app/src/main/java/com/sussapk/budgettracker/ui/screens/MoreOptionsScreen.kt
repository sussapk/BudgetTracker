package com.sussapk.budgettracker.ui.screens

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sussapk.budgettracker.ui.components.ProfileCard
import com.sussapk.budgettracker.ui.components.SettingsHeader
import com.sussapk.budgettracker.ui.components.SettingsItem
import com.sussapk.budgettracker.utils.BackupManager
import com.sussapk.budgettracker.viewmodel.CashTransactionViewModel
import com.sussapk.budgettracker.viewmodel.CashTransactionViewModelFactory
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModel
import com.sussapk.budgettracker.viewmodel.UserPreferencesViewModelFactory
import kotlinx.coroutines.launch


@Composable
fun MoreOptionsScreen(
    navController: NavHostController
) {

    val viewModel: CashTransactionViewModel = viewModel(
        factory = CashTransactionViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )




    var showImportDialog by remember {
        mutableStateOf(false)
    }

    var message by remember {
        mutableStateOf<String?>(null)
    }

    var showEditProfileDialog by remember {
        mutableStateOf(false)
    }

    var editName by remember {
        mutableStateOf("")
    }

    var editBalance by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current




    val preferencesViewModel: UserPreferencesViewModel = viewModel(
        factory = UserPreferencesViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )

    val username by preferencesViewModel.userName.collectAsState()

    val initialBalance by preferencesViewModel.initialBalance.collectAsState()

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->

        if (uri == null) return@rememberLauncherForActivityResult

        scope.launch {

            val transactions = viewModel.getAllTransactionsList()

            val json = BackupManager.convertToJson(transactions)

            context.contentResolver.openOutputStream(uri)?.use {

                it.write(json.toByteArray())

            }

        }

    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        if (uri == null) return@rememberLauncherForActivityResult

        scope.launch {

            try {

                val json = context.contentResolver
                    .openInputStream(uri)
                    ?.bufferedReader()
                    ?.use { it.readText() }

                if (json != null) {

                    val transactions =
                        BackupManager.convertFromJson(json)

                    if (transactions.isEmpty()) {

                        message = "Selected backup is empty."

                        return@launch

                    }

                    viewModel.restoreBackup(transactions)

                    message = "Backup restored successfully."

                }

            } catch (e: Exception) {

                message = "Invalid backup file."

            }

        }

    }

    Scaffold(


    ) {
        paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentPadding = PaddingValues(
                start = 16.dp,
                top = 60.dp,
                end = 16.dp,
                bottom = 20.dp
            ),

            verticalArrangement = Arrangement.spacedBy(14.dp)

        ) {

            item {

                SettingsHeader("Profile")

            }

            item {

                ProfileCard(

                    username = username,


                    onEditClick = {

                        editName = username

                        editBalance = initialBalance.toString()

                        showEditProfileDialog = true

                    }

                )

            }

            item {

                SettingsHeader("Backup & Restore")

            }

            item {

                SettingsItem(

                    icon = Icons.Default.Upload,

                    title = "Export Backup",

                    onClick = {

                        exportLauncher.launch("budgettracker_backup.json")

                    }

                )

            }

            item {

                SettingsItem(

                    icon = Icons.Default.Download,

                    title = "Import Backup",

                    onClick = {

                        showImportDialog = true

                    }

                )

            }

            item {

                SettingsHeader("About")

            }

            item {

                SettingsItem(

                    icon = Icons.Default.Info,

                    title = "Version 1.0",

                    onClick = { }

                )

            }

        }

    }


    val isEdited =
        editName != username ||
                editBalance != initialBalance.toString()

    if (showEditProfileDialog) {

        AlertDialog(

            onDismissRequest = {
                showEditProfileDialog = false
            },

            title = {
                Text(
                    "Edit Profile",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,

            text = {

                Column {

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),

                        value = editName,

                        onValueChange = {
                            editName = it
                        },

                        label = {
                            Text("Name")
                        }

                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    OutlinedTextField(

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),

                        value = editBalance,

                        onValueChange = {
                            editBalance = it
                        },

                        label = {
                            Text("Initial Balance")
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )

                    )

                }

            },

            confirmButton = {

                Button(

                    onClick = {

                        preferencesViewModel.updateUserName(editName)

                        preferencesViewModel.updateInitialBalance(
                            editBalance.toDoubleOrNull() ?: 0.0
                        )

                        showEditProfileDialog = false

                    },

                    enabled = isEdited,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                ) {

                    Text("Save")

                }

            },

            dismissButton = {

                OutlinedButton(

                    onClick = {

                        showEditProfileDialog = false

                    }

                ) {

                    Text("Cancel")

                }

            }

        )

    }


    if (showImportDialog) {

        AlertDialog(

            onDismissRequest = {
                showImportDialog = false
            },

            title = {
                Text(
                    "Restore Backup",
                    color = MaterialTheme.colorScheme.primary
                )
            },

            text = {
                Text(
                    "This will replace all current transactions with the selected backup.\n\nDo you want to continue?"
                )
            },

            confirmButton = {

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),

                    onClick = {

                        showImportDialog = false

                        importLauncher.launch(
                            arrayOf("application/json")
                        )

                    },


                ) {

                    Text("Restore")

                }

            },

            dismissButton = {

                OutlinedButton(

                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),

                    onClick = {

                        showImportDialog = false

                    }

                ) {

                    Text("Cancel")

                }

            }

        )

    }
    message?.let {

        LaunchedEffect(it) {

            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()

            message = null

        }

    }
}