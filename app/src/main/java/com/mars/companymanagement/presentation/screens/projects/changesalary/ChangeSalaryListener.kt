package com.mars.companymanagement.presentation.screens.projects.changesalary

interface ChangeSalaryListener {
    fun salaryChanged(salary: Float) = Unit

    fun salaryChangeDismissed() = Unit
}
