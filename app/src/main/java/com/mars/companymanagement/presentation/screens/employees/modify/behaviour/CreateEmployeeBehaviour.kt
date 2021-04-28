package com.mars.companymanagement.presentation.screens.employees.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.presentation.screens.employees.modify.models.EmployeeChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class CreateEmployeeBehaviour : ChangeEmployeeBehaviour {
    override fun getPreliminaryData(): Employee? = null

    override suspend fun applyChanges(repository: EmployeesRepository, changes: EmployeeChanges): RequestResult<Employee> {
        return RequestResult.UnknownError()
    }
}