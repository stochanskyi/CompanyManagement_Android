package com.mars.companymanagement.data.repositories.transactions.model

import java.time.LocalDate

data class CustomerTransactionDetails(
    val id: String,
    val amount: Float,
    val paymentDate: LocalDate,
    val customer: TransactionCustomer,
    val confirmer: Confirmer
)