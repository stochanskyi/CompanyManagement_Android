package com.mars.companymanagement.data.repositories.transactions.model

import java.time.LocalDate

data class EmployeeTransactionDetails(
    val id: String,
    val amount: Float,
    val date: LocalDate,
    val employee: TransactionEmployee,
    val confirmer: Confirmer
)