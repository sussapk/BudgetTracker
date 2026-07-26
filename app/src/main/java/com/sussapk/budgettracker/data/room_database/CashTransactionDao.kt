package com.sussapk.budgettracker.data.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CashTransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: CashTransaction)


    @Delete
    suspend fun delete(transaction: CashTransaction)

    @Update
    suspend fun update(transaction: CashTransaction)

    @Query("SELECT * FROM cash_transactions ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<CashTransaction>>


    @Query("SELECT * FROM cash_transactions ORDER BY id DESC")
    suspend fun getAllTransactionsList(): List<CashTransaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        transactions: List<CashTransaction>
    )

    @Query("DELETE FROM cash_transactions")
    suspend fun deleteAll()

    @Query("SELECT * FROM cash_transactions WHERE id = :id LIMIT 1")
    suspend fun getTransactionById(id: Int): CashTransaction?
}