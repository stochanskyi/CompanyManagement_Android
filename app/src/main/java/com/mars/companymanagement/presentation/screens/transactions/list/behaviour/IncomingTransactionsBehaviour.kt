package com.mars.companymanagement.presentation.screens.transactions.list.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.transactions.TransactionsRepository
import com.mars.companymanagement.data.repositories.transactions.model.Transaction

class IncomingTransactionsBehaviour : TransactionsBehaviour {

    override val observeIncoming: Boolean = true

    override val observeOutgoing: Boolean = false

    override suspend fun loadTransactions(repository: TransactionsRepository): RequestResult<List<Transaction>> {
        return repository.getTransactions().map { it.filter { it.isIncome } }
    }
}