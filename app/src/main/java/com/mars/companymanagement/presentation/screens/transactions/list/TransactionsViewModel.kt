package com.mars.companymanagement.presentation.screens.transactions.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.transactions.TransactionsRepository
import com.mars.companymanagement.data.repositories.transactions.model.Transaction
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.AllTransactionsBehaviour
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.IncomingTransactionsBehaviour
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.OutgoingTransactionsBehaviour
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.TransactionsBehaviour
import com.mars.companymanagement.presentation.screens.transactions.list.model.TransactionViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : BaseViewModel() {

    private val moneyFormatter = DecimalFormat("0.00").apply {
        this.decimalFormatSymbols.currencySymbol = ","
    }

    private val _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _transactionsLiveData: MutableLiveData<List<TransactionViewData>> =
        MutableLiveData()
    val transactionsLiveData: LiveData<List<TransactionViewData>> = _transactionsLiveData

    private val _openCreateEmployeeTransaction: SingleLiveData<Unit> = SingleLiveData()
    val openCreateEmployeeTransaction: LiveData<Unit> = _openCreateEmployeeTransaction

    private val _openCreateCustomerTransaction: SingleLiveData<Unit> = SingleLiveData()
    val openCreateCustomerTransaction: LiveData<Unit> = _openCreateCustomerTransaction

    private val _openSelectTransactionTypeLiveData: SingleLiveData<Unit> = SingleLiveData()
    val openSelectTransactionTypeLiveData: LiveData<Unit> = _openSelectTransactionTypeLiveData

    private val transactions: MutableList<Transaction> = mutableListOf()

    private lateinit var behaviour: TransactionsBehaviour

    fun setup(behaviour: TransactionsBehaviour) {
        this.behaviour = behaviour
        observeChanges()
        loadTransactions()
    }

    fun addTransaction() {
        when (behaviour) {
            is AllTransactionsBehaviour -> _openSelectTransactionTypeLiveData.call()
            is IncomingTransactionsBehaviour -> _openCreateCustomerTransaction.call()
            is OutgoingTransactionsBehaviour -> _openCreateEmployeeTransaction.call()
        }
    }

    private fun observeChanges() {
        if (behaviour.observeIncoming) observeIncomingTransactions()
        if (behaviour.observeOutgoing) observeOutgoingTransactions()
    }

    private fun observeIncomingTransactions() {
        viewModelScope.launch {
            transactionsRepository.incomingTransactionFlow.collect {
                transactions.add(0, it)
                updateTransactionsList()
            }
        }
    }

    private fun observeOutgoingTransactions() {
        viewModelScope.launch {
            transactionsRepository.outgoingTransactionFlow.collect {
                transactions.add(0, it)
                updateTransactionsList()
            }
        }
    }

    private fun loadTransactions() {
        viewModelScope.launchSafeCall(_loadingLiveData) {
            val loadedTransactions = safeRequestCall { behaviour.loadTransactions(transactionsRepository) } ?: return@launchSafeCall

            transactions.apply {
                clear()
                addAll(loadedTransactions.asReversed())
            }

            updateTransactionsList()
        }
    }

    fun openTransactionDetails(id: String) {
        //TODO Implement later
    }

    private fun updateTransactionsList() {
        _transactionsLiveData.value = transactions.map { it.toViewData() }
    }

    private fun Transaction.toViewData() = TransactionViewData(
        id,
        person,
        formatDate(date),
        formatMoney(amount, isIncome),
        isIncome
    )

    private fun formatMoney(amount: Float, isIncoming: Boolean): String {
        val prefix = if (isIncoming) "+" else "-"
        return "$prefix${moneyFormatter.format(amount)}"
    }

    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return date.format(formatter)
    }
}