package com.mars.companymanagement.presentation.screens.transactions.list.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.transactions.TransactionsRepository
import com.mars.companymanagement.data.repositories.transactions.model.Transaction

class OutgoingTransactionsBehaviour : TransactionsBehaviour{

    override val observeIncoming: Boolean = false

    override val observeOutgoing: Boolean = true

    override suspend fun loadTransactions(repository: TransactionsRepository): RequestResult<List<Transaction>> {
        return repository.getTransactions().map { it.filter { !it.isIncome } }
    }
}