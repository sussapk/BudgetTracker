package com.sussapk.budgettracker.utils


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sussapk.budgettracker.data.room_database.CashTransaction

object BackupManager {

    private val gson = Gson()

    fun convertToJson(
        transactions: List<CashTransaction>
    ): String {

        return gson.toJson(transactions)

    }

    fun convertFromJson(
        json: String
    ): List<CashTransaction> {

        val type = object : TypeToken<List<CashTransaction>>() {}.type

        return gson.fromJson(json, type)

    }

}