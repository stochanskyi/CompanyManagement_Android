package com.mars.companymanagement.presentation.screens.customers.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.details.models.CustomerInfoViewData
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    private val customersRepository: CustomersRepository
) : BaseViewModel() {

    private val _customerInfoLiveData: MutableLiveData<CustomerInfoViewData> = MutableLiveData()
    val customerInfoLiveData: LiveData<CustomerInfoViewData> = _customerInfoLiveData

    private val _projectsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectsLoadingLiveData: LiveData<Boolean> = _projectsLoadingLiveData

    private val _projectsLiveData: MutableLiveData<List<ProjectViewData>> = MutableLiveData()
    val projectsLiveData: LiveData<List<ProjectViewData>> = _projectsLiveData

    private val _openProjectDetailsLiveData: MutableLiveData<Project> = SingleLiveData()
    val openProjectDetailsLiveData: LiveData<Project> = _openProjectDetailsLiveData

    private val _editCustomerLiveData: MutableLiveData<Customer> = SingleLiveData()
    val editCustomerLiveData: LiveData<Customer> = _editCustomerLiveData

    private lateinit var projects: List<Project>

    private lateinit var customer: Customer

    init {
        viewModelScope.launch {
            customersRepository.customerUpdatedFlow.collect {
                if (it.id != customer.id) return@collect

                customer = customer.copy(
                    firstName = it.firstName,
                    lastName = it.lastName,
                    country = it.country,
                    email = it.email,
                    phoneNumber = it.phoneNumber
                )
                _customerInfoLiveData.value = customer.toViewData()
            }
        }
    }

    fun setup(customer: Customer) {
        this.customer = customer
        _customerInfoLiveData.value = customer.toViewData()
        loadProjects(customer.id)
    }

    fun openProjectDetails(projectId: String) {
        _openProjectDetailsLiveData.value = projects.firstOrNull { it.id == projectId } ?: return
    }

    fun editCustomer() {
        _editCustomerLiveData.value = customer
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