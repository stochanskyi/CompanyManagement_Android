package com.mars.companymanagement.presentation.screens.projects.employeeselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.projects.employeeselection.models.SelectableEmployeeViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeSelectionViewModel @Inject constructor(
    private val repository: EmployeesRepository
) : BaseViewModel() {

    private lateinit var employees: List<Employee>

    private val checkedEmployees: MutableList<Employee> = mutableListOf()

    private val _employeesLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val employeesLoadingLiveData: LiveData<Boolean> = _employeesLoadingLiveData

    private val _employeesLiveData: MutableLiveData<List<SelectableEmployeeViewData>> = MutableLiveData()
    val employeesLiveData: MutableLiveData<List<SelectableEmployeeViewData>> = _employeesLiveData

    private val _checkedEmployeesLiveData: MutableLiveData<List<Employee>> = MutableLiveData()
    val checkedEmployeesLiveData: LiveData<List<Employee>> = _checkedEmployeesLiveData

    fun setup(selectedEmployees: List<String>) {
        loadEmployees(selectedEmployees)
    }

    fun itemSelectionChanged(id: String, isChecked: Boolean) {
        val employee = employees.firstOrNull { it.id == id } ?: return
        if (!isChecked) {
            checkedEmployees.remove(employee)
        } else {
            checkedEmployees += employee
        }
        _checkedEmployeesLiveData.value = checkedEmployees
    }

    private fun loadEmployees(selectedEmployees: List<String>) {
        viewModelScope.launchSafeCall(_employeesLoadingLiveData) {
            employees = safeRequestCall { repository.getEmployees() } ?: return@launchSafeCall

            checkedEmployees.addAll(
                selectedEmployees.mapNotNull { selectedId -> employees.firstOrNull { it.id == selectedId } }
            )

            _employeesLiveData.value = employees.map { it.toViewData(checkedEmployees.contains(it)) }
            _checkedEmployeesLiveData.value = checkedEmployees
        }
    }

    private fun Employee.toViewData(isChecked: Boolean) = SelectableEmployeeViewData(
        id,
        fullName,
        position.name,
        isChecked
    )
}