package com.sussapk.budgettracker.viewmodel



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sussapk.budgettracker.data.room_database.AppDatabase
import com.sussapk.budgettracker.data.room_database.CashTransaction
import com.sussapk.budgettracker.repository.CashTransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CashTransactionViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository: CashTransactionRepository

    val allTransactions: StateFlow<List<CashTransaction>>

    init {

        val dao = AppDatabase.getDatabase(application).cashTransactionDao()

        repository = CashTransactionRepository(dao)

        allTransactions = repository.getAllTransactions().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun insert(transaction: CashTransaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    fun update(transaction: CashTransaction) {
        viewModelScope.launch {
            repository.update(transaction)
        }
    }



    fun delete(transaction: CashTransaction) {
        viewModelScope.launch {
            repository.delete(transaction)
        }
    }

    suspend fun getAllTransactionsList(): List<CashTransaction> {
        return repository.getAllTransactionsList()
    }

    fun restoreBackup(
        transactions: List<CashTransaction>
    ) {

        viewModelScope.launch {

            repository.deleteAll()

            repository.insertAll(transactions)

        }

    }

    suspend fun getTransactionById(
        id: Int
    ): CashTransaction? {

        return repository.getTransactionById(id)

    }


}