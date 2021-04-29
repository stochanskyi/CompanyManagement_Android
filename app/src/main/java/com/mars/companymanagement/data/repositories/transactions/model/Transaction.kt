package com.mars.companymanagement.data.repositories.transactions.model

import java.time.LocalDate

data class Transaction(
    val id: String,
    val person: String,
    val amount: Float,
    val date: LocalDate,
    val isIncome: Boolean
)