package com.mars.companymanagement.presentation.screens.employees.modify.behaviour

import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.presentation.screens.employees.modify.models.EmployeeChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class EditEmployeeBehaviour(private val preliminaryData: Employee) : ChangeEmployeeBehaviour {
    override fun getPreliminaryData() = preliminaryData

    override suspend fun applyChanges(repository: EmployeesRepository, changes: EmployeeChanges) {
        //TODO
    }
}