package com.mars.companymanagement.presentation.screens.employees.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.details.models.EmployeeDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    private val employeesRepository: EmployeesRepository
) : BaseViewModel() {

    private val _employeeInfoViewData: MutableLiveData<EmployeeDetailsViewData> = MutableLiveData()
    val employeeInfoViewData: LiveData<EmployeeDetailsViewData> = _employeeInfoViewData

    private val _projectsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectsLoadingLiveData: LiveData<Boolean> = _projectsLoadingLiveData

    private val _projectsLiveData: MutableLiveData<List<ProjectViewData>> = MutableLiveData()
    val projectsLiveData: LiveData<List<ProjectViewData>> = _projectsLiveData

    private val _openProjectDetailsLiveData: MutableLiveData<Project> = SingleLiveData()
    val openProjectDetailsLiveData: LiveData<Project> = _openProjectDetailsLiveData

    private val _openEditEmployeeLiveData: MutableLiveData<Employee> = SingleLiveData()
    val openEditEmployeeLiveData: LiveData<Employee> = _openEditEmployeeLiveData

    private lateinit var employee: Employee
    private lateinit var projects: List<Project>

    init {
        viewModelScope.launch {
            employeesRepository.employeeUpdatedFlow.collect {
                if (employee.id == it.id) {
                    employee = employee.copy(
                        firstName = it.firstName,
                        lastName = it.lastName,
                        email = it.email,
                        position = it.position
                    )
                    _employeeInfoViewData.value = employee.toViewData()
                }
            }
        }
    }

    fun setup(employee: Employee) {
        this.employee = employee
        _employeeInfoViewData.value = employee.toViewData()
        loadProjects()
    }

    fun openProjectDetails(projectId: String) {
        _openProjectDetailsLiveData.value = projects.firstOrNull { it.id == projectId } ?: return
    }

    fun openEditEmployee() {
        _openEditEmployeeLiveData.value = employee
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