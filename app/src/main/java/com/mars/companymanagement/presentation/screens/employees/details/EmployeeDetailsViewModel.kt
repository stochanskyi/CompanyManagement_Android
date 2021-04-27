package com.mars.companymanagement.presentation.screens.employees.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.details.models.EmployeeDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {

    private val _employeeInfoViewData: MutableLiveData<EmployeeDetailsViewData> = MutableLiveData()
    val employeeInfoViewData: LiveData<EmployeeDetailsViewData> = _employeeInfoViewData

    private val _projectsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectsLoadingLiveData: LiveData<Boolean> = _projectsLoadingLiveData

    private val _projectsLiveData: MutableLiveData<List<ProjectViewData>> = MutableLiveData()
    val projectsLiveData: LiveData<List<ProjectViewData>> = _projectsLiveData

    private lateinit var employee: Employee
    private lateinit var projects: List<Project>

    fun setup(employee: Employee) {
        this.employee = employee
        _employeeInfoViewData.value = employee.toViewData()
        loadProjects()
    }

    fun openProjectDetails(projectId: String) {
        //TODO
    }

    private fun loadProjects() {
        viewModelScope.launchSafeCall(_projectsLoadingLiveData) {
            projects = safeRequestCall { projectsRepository.getProjectsForEmployee(employee.id) }
                ?: return@launchSafeCall
            _projectsLiveData.value = projects.map { it.toViewData() }
        }
    }

    private fun Employee.toViewData() = EmployeeDetailsViewData(fullName, position.name, email)

    private fun Project.toViewData() = ProjectViewData(id, name, status.name)
}