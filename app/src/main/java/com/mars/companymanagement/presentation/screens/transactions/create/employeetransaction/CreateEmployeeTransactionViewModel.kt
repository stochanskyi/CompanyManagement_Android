package com.mars.companymanagement.presentation.screens.transactions.create.employeetransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.transactions.TransactionsRepository
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.transactions.create.employeetransaction.models.EmployeeTransactionInfoViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEmployeeTransactionViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) :BaseViewModel() {
    private var employeeId: String? = null
    private var amount: Float? = null

    private val _closeScreenLiveData: SingleLiveData<Unit> = SingleLiveData()
    val closeScreenLiveData: MutableLiveData<Unit> = _closeScreenLiveData

    private val _employeeInfoLiveData: MutableLiveData<EmployeeTransactionInfoViewData> = MutableLiveData()
    val employeeInfoLiveData: LiveData<EmployeeTransactionInfoViewData> = _employeeInfoLiveData

    fun amountChanged(amount: String) {
        this.amount = amount.toFloatOrNull()
    }

    fun selectedEmployeeChanged(employee: Employee) {
        employeeId = employee.id
        _employeeInfoLiveData.value = employee.toViewData()
    }

    fun createTransaction() {
        val safeEmployeeId = employeeId ?: return
        val safeAmount = amount ?: return

        viewModelScope.launch {
            safeRequestCall {
                transactionsRepository.createEmployeeTransaction(safeEmployeeId, safeAmount)
            } ?: return@launch
            _closeScreenLiveData.call()
        }
    }

    private fun Employee.toViewData() = EmployeeTransactionInfoViewData(fullName, position.name)
}