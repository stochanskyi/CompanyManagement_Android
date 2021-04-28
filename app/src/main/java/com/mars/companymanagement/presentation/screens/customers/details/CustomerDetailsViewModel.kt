package com.mars.companymanagement.presentation.screens.customers.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.details.models.CustomerInfoViewData
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {

    private val _customerInfoLiveData: MutableLiveData<CustomerInfoViewData> = MutableLiveData()
    val customerInfoLiveData: LiveData<CustomerInfoViewData> = _customerInfoLiveData

    private val _projectsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectsLoadingLiveData: LiveData<Boolean> = _projectsLoadingLiveData

    private val _projectsLiveData: MutableLiveData<List<ProjectViewData>> = MutableLiveData()
    val projectsLiveData: LiveData<List<ProjectViewData>> = _projectsLiveData

    private val _openProjectDetailsLiveData: MutableLiveData<Project> = SingleLiveData()
    val openProjectDetailsLiveData: LiveData<Project> = _openProjectDetailsLiveData

    private lateinit var projects: List<Project>

    fun setup(customer: Customer) {
        _customerInfoLiveData.value = customer.toViewData()
        loadProjects(customer.id)
    }

    fun openProjectDetails(projectId: String) {
        _openProjectDetailsLiveData.value = projects.firstOrNull { it.id == projectId } ?: return
    }

    private fun loadProjects(customerId: String) {
        viewModelScope.launchSafeCall(_projectsLoadingLiveData) {
            projects = safeRequestCall {
                projectsRepository.getCustomerProjects(customerId)
            } ?: return@launchSafeCall

            _projectsLiveData.value = projects.map { it.toViewData() }
        }
    }

    private fun Customer.toViewData() = CustomerInfoViewData(fullName, country, email, phoneNumber)

    private fun Project.toViewData() = ProjectViewData(id, name, status.name)
}