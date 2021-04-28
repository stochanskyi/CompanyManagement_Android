package com.mars.companymanagement.presentation.screens.employees.modify.behaviour

import android.os.Parcelable
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.presentation.screens.employees.modify.models.EmployeeChanges

interface ChangeEmployeeBehaviour : Parcelable{

    fun getPreliminaryData(): Employee?

    suspend fun applyChanges(repository: EmployeesRepository, changes: EmployeeChanges)
}