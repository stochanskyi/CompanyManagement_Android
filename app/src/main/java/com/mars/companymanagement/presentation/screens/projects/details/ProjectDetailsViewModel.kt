package com.mars.companymanagement.presentation.screens.projects.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.details.models.EmployeeDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectDetailsViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
): BaseViewModel() {

    private val _preliminaryProjectInfoLiveData: MutableLiveData<PreliminaryProjectViewData> = MutableLiveData()
    val preliminaryProjectInfoLiveData: LiveData<PreliminaryProjectViewData> = _preliminaryProjectInfoLiveData

    private val _projectDetailsLiveData: MutableLiveData<ProjectDetailsViewData> = MutableLiveData()
    val projectDetailsLiveData: LiveData<ProjectDetailsViewData> = _projectDetailsLiveData

    private val _projectDetailsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectDetailsLoadingLiveData: LiveData<Boolean> = _projectDetailsLoadingLiveData

    private val _openCustomerDetailsLiveData: MutableLiveData<Customer> = SingleLiveData()
    val openCustomerDetailsLiveData: LiveData<Customer> = _openCustomerDetailsLiveData

    private val _openEditProjectLiveData: MutableLiveData<ProjectDetails> = SingleLiveData()
    val openEditProjectLiveData: LiveData<ProjectDetails> = _openEditProjectLiveData

    private lateinit var projectDetails: ProjectDetails

    fun setup(project: Project) {
        _preliminaryProjectInfoLiveData.value = project.toViewData()
        loadProjectDetails(project.id)
    }

    private fun loadProjectDetails(id: String) {
        viewModelScope.launchSafeCall(_projectDetailsLoadingLiveData) {
            projectDetails = safeRequestCall { projectsRepository.getProjectDetails(id) }
                ?: return@launchSafeCall
            _projectDetailsLiveData.value = projectDetails.toViewData()
        }
    }

    fun openCustomerDetails() {
        _openCustomerDetailsLiveData.value = projectDetails.projectOwner
    }

    fun editProject() {
        _openEditProjectLiveData.value = projectDetails
    }

    private fun Employee.toViewData() = EmployeeDetailsViewData(fullName, position.name, email)

    private fun Project.toViewData() = PreliminaryProjectViewData(name, status.name)

    private fun ProjectDetails.toViewData() = ProjectDetailsViewData(
        formatDeadline(deadline),
        description,
        projectOwner.fullName,
        projectOwner.country
    )

    private fun formatDeadline(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return date.format(formatter)
    }
}