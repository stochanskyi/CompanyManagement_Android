package com.mars.companymanagement.presentation.screens.projects.modify.models

import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.models.Employee
import java.time.LocalDate

class ProjectChanges {
    var name: String? = null
    var description: String? = null
    var deadline: LocalDate? = null
    var ownerId: String? = null
    var employees: List<Employee>? = null
    var statusId: String? = null
}