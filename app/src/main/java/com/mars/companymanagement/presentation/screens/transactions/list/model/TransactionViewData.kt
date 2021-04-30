package com.mars.companymanagement.presentation.screens.transactions.list.model

data class TransactionViewData(
    val id: String,
    val title: String,
    val date: String,
    val amount: String,
    val isIncoming: Boolean
)