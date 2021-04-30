package com.mars.companymanagement.presentation.screens.employeeSelection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.list.models.ItemViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleEmployeeSelectionViewModel @Inject constructor(
    private val employeesRepository: EmployeesRepository
) : BaseViewModel() {

    private val _employeesLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val employeesLoadingLiveData: LiveData<Boolean> = _employeesLoadingLiveData

    private val _employeesLiveData: MutableLiveData<List<ItemViewData>> = MutableLiveData()
    val employeesLiveData: LiveData<List<ItemViewData>> = _employeesLiveData

    private val _customerSelectedLiveData: MutableLiveData<Employee> = MutableLiveData()
    val customerSelectedLiveData: LiveData<Employee> = _customerSelectedLiveData

    private lateinit var employees: List<Employee>

    init {
        loadCustomers()
    }

    fun selectCustomer(id: String) {
        val employee = employees.firstOrNull { it.id == id } ?: return
        _customerSelectedLiveData.value = employee
    }

    private fun loadCustomers() {
        viewModelScope.launchSafeCall(_employeesLoadingLiveData) {
            employees =
                safeRequestCall { employeesRepository.getEmployees() } ?: return@launchSafeCall

            _employeesLiveData.value =
                employees.map { ItemViewData(it.id, it.fullName, it.position.name) }
        }
    }
}