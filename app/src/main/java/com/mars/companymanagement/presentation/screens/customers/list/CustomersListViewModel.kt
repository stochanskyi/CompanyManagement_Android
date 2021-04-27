package com.mars.companymanagement.presentation.screens.customers.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.list.models.CustomerViewData
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var customers: List<Customer> = emptyList()

    init {
        loadCustomers()
    }

    fun openCustomerDetails(customerId: String) {
        //TODO
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            customers = safeRequestCallWithLoading(_customersLoadingLiveData) {
                customersRepository.getCustomers()
            } ?: return@launch
            _customersLiveData.value = customers.map { it.toViewData() }
        }
    }

    private fun Customer.toViewData() = CustomerViewData(id, fullName, country)

}