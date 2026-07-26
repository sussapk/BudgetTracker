package com.sussapk.budgettracker.data.room_database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cash_transactions")
data class CashTransaction(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,

    val type: String,

    val category: String,

    val date: String,

    val time: String
)