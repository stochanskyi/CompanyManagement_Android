package com.mars.companymanagement.presentation.screens.transactions.list.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.transactions.TransactionsRepository
import com.mars.companymanagement.data.repositories.transactions.model.Transaction

interface TransactionsBehaviour {

    val observeIncoming: Boolean

    val observeOutgoing: Boolean

    suspend fun loadTransactions(repository: TransactionsRepository): RequestResult<List<Transaction>>
}