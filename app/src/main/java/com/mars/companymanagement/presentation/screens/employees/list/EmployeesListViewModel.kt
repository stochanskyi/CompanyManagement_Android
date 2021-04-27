package com.mars.companymanagement.presentation.screens.employees.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.list.models.EmployeeViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesListViewModel @Inject constructor(
        private val employeesRepository: EmployeesRepository
) : BaseViewModel() {

    private val _employeesLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val employeesLoadingLiveData: LiveData<Boolean> = _employeesLoadingLiveData

    private val _employeesLiveData: MutableLiveData<List<EmployeeViewData>> = MutableLiveData()
    val employeesLiveData: LiveData<List<EmployeeViewData>> = _employeesLiveData

    private val _openEmployeeDetailsLiveData: MutableLiveData<Employee> = SingleLiveData()
    val openEmployeeDetailsLiveData: LiveData<Employee> = _openEmployeeDetailsLiveData

    private var employees: List<Employee> = emptyList()
        set(value) {
            field = value
            _employeesLiveData.value = value.toViewData()
        }

    init {
        loadEmployees()
    }

    fun openEmployeeInfo(id: String) {
        _openEmployeeDetailsLiveData.value = employees.firstOrNull { it.id == id } ?: return
    }

    private fun loadEmployees() {
        viewModelScope.launch {
            employees = safeRequestCallWithLoading(_employeesLoadingLiveData) {
                employeesRepository.getEmployees()
            } ?: return@launch
        }
    }

    private fun List<Employee>.toViewData(): List<EmployeeViewData> {
        return map {
            EmployeeViewData(it.id, it.fullName, it.position.name)
        }
    }
}