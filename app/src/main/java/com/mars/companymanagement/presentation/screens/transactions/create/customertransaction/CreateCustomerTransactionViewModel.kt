package com.mars.companymanagement.presentation.screens.transactions.create.customertransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.data.repositories.transactions.TransactionsRepository
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.transactions.create.customertransaction.models.CustomerTransactionInfoViewData
import com.mars.companymanagement.presentation.screens.transactions.create.customertransaction.models.ProjectTransactionInfoViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCustomerTransactionViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : BaseViewModel() {

    private var customerId: String? = null
    private var projectId: String? = null
    private var amount: Float? = null

    private val _closeScreenLiveData: SingleLiveData<Unit> = SingleLiveData()
    val closeScreenLiveData: MutableLiveData<Unit> = _closeScreenLiveData

    private val _customerInfoLiveData: MutableLiveData<CustomerTransactionInfoViewData> = MutableLiveData()
    val customerInfoLiveData: LiveData<CustomerTransactionInfoViewData> = _customerInfoLiveData

    private val _projectInfoLiveData: MutableLiveData<ProjectTransactionInfoViewData> = MutableLiveData()
    val projectInfoLiveData: LiveData<ProjectTransactionInfoViewData> = _projectInfoLiveData

    fun amountChanged(amount: String) {
        this.amount = amount.toFloatOrNull()
    }

    fun selectedCustomerChanged(customer: Customer) {
        customerId = customer.id
        _customerInfoLiveData.value = customer.toViewData()
    }

    fun selectedProjectChanged(project: Project) {
        projectId = project.id
        _projectInfoLiveData.value = project.toViewData()
    }

    fun createTransaction() {
        val safeCustomerId = customerId ?: return
        val safeProjectId = projectId ?: return
        val safeAmount = amount ?: return

        viewModelScope.launch {
            safeRequestCall {
                transactionsRepository.createCustomerTransaction(safeCustomerId, safeProjectId, safeAmount)
            } ?: return@launch
            _closeScreenLiveData.call()
        }
    }

    private fun Customer.toViewData() = CustomerTransactionInfoViewData(fullName, country)

    private fun Project.toViewData() = ProjectTransactionInfoViewData(name, status.name)
}