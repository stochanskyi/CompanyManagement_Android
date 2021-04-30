package com.mars.companymanagement.presentation.screens.projects.customerselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.list.models.ItemViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerSelectionViewModel @Inject constructor(
    private val customersRepository: CustomersRepository
) : BaseViewModel() {

    private val _customersLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val customersLoadingLiveData: LiveData<Boolean> = _customersLoadingLiveData

    private val _customersLiveData: MutableLiveData<List<ItemViewData>> = MutableLiveData()
    val customersLiveData: LiveData<List<ItemViewData>> = _customersLiveData

    private val _customerSelectedLiveData: MutableLiveData<Customer> = MutableLiveData()
    val customerSelectedLiveData: LiveData<Customer> = _customerSelectedLiveData

    private lateinit var customers: List<Customer>

    init {
        loadCustomers()
    }

    fun selectCustomer(id: String) {
        val customer = customers.firstOrNull { it.id == id } ?: return
        _customerSelectedLiveData.value = customer
    }

    private fun loadCustomers() {
        viewModelScope.launchSafeCall(_customersLoadingLiveData) {
            customers = safeRequestCall { customersRepository.getCustomers() } ?: return@launchSafeCall

            _customersLiveData.value = customers.map { ItemViewData(it.id, it.fullName, it.country) }
        }
    }

}