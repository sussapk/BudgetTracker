package com.sussapk.budgettracker.repository

import com.sussapk.budgettracker.data.room_database.CashTransaction
import com.sussapk.budgettracker.data.room_database.CashTransactionDao
import kotlinx.coroutines.flow.Flow

class CashTransactionRepository(
    private val cashTransactionDao: CashTransactionDao
) {

    suspend fun insert(transaction: CashTransaction) {
        cashTransactionDao.insert(transaction)
    }


    suspend fun delete(transaction: CashTransaction) {
        cashTransactionDao.delete(transaction)
    }

    suspend fun update(transaction: CashTransaction) {
        cashTransactionDao.update(transaction)
    }

     fun getAllTransactions(): Flow<List<CashTransaction>> {
        return cashTransactionDao.getAllTransactions()
    }

    suspend fun getAllTransactionsList(): List<CashTransaction> {
        return cashTransactionDao.getAllTransactionsList()
    }

    suspend fun insertAll(
        transactions: List<CashTransaction>
    ) {
        cashTransactionDao.insertAll(transactions)
    }

    suspend fun deleteAll() {
        cashTransactionDao.deleteAll()
    }

    suspend fun getTransactionById(
        id: Int
    ): CashTransaction? {

        return cashTransactionDao.getTransactionById(id)

    }
}