package com.mars.companymanagement.presentation.screens.projects.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectEmployee
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.details.models.EmployeeDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectEmployeeViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
): BaseViewModel() {

    private val moneyFormatter = DecimalFormat("0.00").apply {
        this.decimalFormatSymbols.currencySymbol = ","
    }

    private val _preliminaryProjectInfoLiveData: MutableLiveData<PreliminaryProjectViewData> = MutableLiveData()
    val preliminaryProjectInfoLiveData: LiveData<PreliminaryProjectViewData> = _preliminaryProjectInfoLiveData

    private val _projectDetailsLiveData: MutableLiveData<ProjectDetailsViewData> = MutableLiveData()
    val projectDetailsLiveData: LiveData<ProjectDetailsViewData> = _projectDetailsLiveData

    private val _projectDetailsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectDetailsLoadingLiveData: LiveData<Boolean> = _projectDetailsLoadingLiveData

    private val _employeesLiveData: MutableLiveData<List<ProjectEmployeeViewData>> = MutableLiveData()
    val employeesLiveData: LiveData<List<ProjectEmployeeViewData>> = _employeesLiveData

    private val _openCustomerDetailsLiveData: MutableLiveData<Customer> = SingleLiveData()
    val openCustomerDetailsLiveData: LiveData<Customer> = _openCustomerDetailsLiveData

    private val _openEditProjectLiveData: MutableLiveData<ProjectDetails> = SingleLiveData()
    val openEditProjectLiveData: LiveData<ProjectDetails> = _openEditProjectLiveData

    private lateinit var projectDetails: ProjectDetails

    init {
        viewModelScope.launch {
            projectsRepository.projectUpdatedFlow.collect {
                if (projectDetails.id != it.id) return@collect

                projectDetails = it
                _preliminaryProjectInfoLiveData.value = PreliminaryProjectViewData(projectDetails.name, projectDetails.status.name)
                _projectDetailsLiveData.value = projectDetails.toViewData()
            }
        }
    }

    fun setup(project: Project) {
        _preliminaryProjectInfoLiveData.value = project.toViewData()
        loadProjectDetails(project.id)
    }

    private fun loadProjectDetails(id: String) {
        viewModelScope.launchSafeCall(_projectDetailsLoadingLiveData) {
            projectDetails = safeRequestCall { projectsRepository.getProjectDetails(id) }
                ?: return@launchSafeCall
            _projectDetailsLiveData.value = projectDetails.toViewData()
            _employeesLiveData.value = projectDetails.employees.map { it.toViewData() }
        }
    }

    fun openCustomerDetails() {
        _openCustomerDetailsLiveData.value = projectDetails.projectOwner
    }

    fun editProject() {
        _openEditProjectLiveData.value = projectDetails
    }

    fun employeeSelected(id: String) {

    }

    private fun Employee.toViewData() = EmployeeDetailsViewData(fullName, position.name, email)

    private fun Project.toViewData() = PreliminaryProjectViewData(name, status.name)

    private fun ProjectDetails.toViewData() = ProjectDetailsViewData(
        formatDeadline(deadline),
        description,
        moneyFormatter.format(salary),
        projectOwner.fullName,
        projectOwner.country
    )

    private fun formatDeadline(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return date.format(formatter)
    }

    private fun ProjectEmployee.toViewData() = ProjectEmployeeViewData(
        id, fullName, position.name, salary?.let { moneyFormatter.format(it) } ?: "-"
    )
}