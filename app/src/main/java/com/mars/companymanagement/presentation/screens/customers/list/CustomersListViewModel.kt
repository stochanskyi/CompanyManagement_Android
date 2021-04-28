package com.mars.companymanagement.presentation.screens.customers.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.list.models.CustomerViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersListViewModel @Inject constructor(
    private val customersRepository: CustomersRepository
) : BaseViewModel() {
    private val _customersLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val customersLoadingLiveData: LiveData<Boolean> = _customersLoadingLiveData

    private val _customersLiveData: MutableLiveData<List<CustomerViewData>> = MutableLiveData()
    val customersLiveData: LiveData<List<CustomerViewData>> = _customersLiveData

    private val _openCustomerDetailsLiveData: MutableLiveData<Customer> = SingleLiveData()
    val openCustomerDetailsLiveData: LiveData<Customer> = _openCustomerDetailsLiveData

    private val customers: MutableList<Customer> = mutableListOf()

    init {
        viewModelScope.launch {
            customersRepository.customerUpdatedFlow.collect { customer ->
                val customerIndex = customers.indexOfFirst { it.id == customer.id }.takeIf { it >= 0 } ?: return@collect

                customers.apply {
                    removeAt(customerIndex)
                    add(customerIndex, customer)
                }
                _customersLiveData.value = customers.map { it.toViewData() }
            }
        }

        viewModelScope.launch {
            customersRepository.customerCreatedFlow.collect { customer ->
                customers += customer
                _customersLiveData.value = customers.map { it.toViewData() }
            }
        }

        loadCustomers()
    }

    fun openCustomerDetails(customerId: String) {
        _openCustomerDetailsLiveData.value = customers.firstOrNull { it.id == customerId } ?: return
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            val loadedCustomers = safeRequestCallWithLoading(_customersLoadingLiveData) {
                customersRepository.getCustomers()
            } ?: return@launch

            customers.clear()
            customers.addAll(loadedCustomers)

            _customersLiveData.value = customers.map { it.toViewData() }
        }
    }

    private fun Customer.toViewData() = CustomerViewData(id, fullName, country)

}