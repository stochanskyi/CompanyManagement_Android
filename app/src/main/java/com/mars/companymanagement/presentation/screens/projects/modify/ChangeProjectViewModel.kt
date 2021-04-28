package com.mars.companymanagement.presentation.screens.projects.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.taxonomies.TaxonomyRepository
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerValidationErrorViewData
import com.mars.companymanagement.presentation.screens.employees.list.models.EmployeeViewData
import com.mars.companymanagement.presentation.screens.projects.modify.behaviour.ChangeProjectBehaviour
import com.mars.companymanagement.presentation.screens.projects.modify.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.screens.projects.modify.models.ProjectChanges
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChangeProjectViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    private val taxonomyRepository: TaxonomyRepository
) : BaseViewModel() {

    private val changes = ProjectChanges()

    private lateinit var behaviour: ChangeProjectBehaviour

    private val _projectStatusesLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val projectStatusesLiveData: MutableLiveData<List<String>> = _projectStatusesLiveData

    private val _preliminaryCustomerLiveData: MutableLiveData<PreliminaryProjectViewData> = SingleLiveData()
    val preliminaryCustomerLiveData: LiveData<PreliminaryProjectViewData> = _preliminaryCustomerLiveData

    private val _closeChangeScreenLiveData: SingleLiveData<Unit> = SingleLiveData()
    val closeChangeScreenLiveData: LiveData<Unit> = _closeChangeScreenLiveData

    private val _validationErrorLiveData: MutableLiveData<CustomerValidationErrorViewData> = SingleLiveData()
    val validationErrorLiveData: LiveData<CustomerValidationErrorViewData> = _validationErrorLiveData

    private val _savingChangesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val savingChangesLiveData: LiveData<Boolean> = _savingChangesLiveData

    private val _projectEmployeesLiveData: MutableLiveData<List<EmployeeViewData>> = MutableLiveData()
    val projectEmployeesLiveData: LiveData<List<EmployeeViewData>> = _projectEmployeesLiveData

    private val _openEmployeesSelectionLiveData: MutableLiveData<List<String>> = SingleLiveData()
    val openEmployeesSelectionLiveData: LiveData<List<String>> = _openEmployeesSelectionLiveData

    fun setup(behaviour: ChangeProjectBehaviour) {
        this.behaviour = behaviour

        _projectStatusesLiveData.value = taxonomyRepository.getProjectStatuses().map { it.name }

        applyPreliminaryData()
    }

    private fun applyPreliminaryData() {
        val preliminaryData = behaviour.getPreliminaryData() ?: return

        changes.apply {
            name = preliminaryData.name
            description = preliminaryData.description
            deadline = preliminaryData.deadline
            ownerId = preliminaryData.projectOwner.id
            employees = preliminaryData.employees
            statusId = preliminaryData.status.id
        }

        _projectEmployeesLiveData.value = preliminaryData.employees.map { EmployeeViewData(it.id, it.fullName, it.position.name) }

        _preliminaryCustomerLiveData.value = PreliminaryProjectViewData(
            preliminaryData.name,
            preliminaryData.description,
            formatDeadline(preliminaryData.deadline),
            preliminaryData.projectOwner.fullName,
            preliminaryData.status.name
        )
    }

    fun nameChanged(name: String) {
        changes.name = name
    }

    fun descriptionChanged(description: String) {
        changes.description = description
    }

    fun deadlineChanged(deadline: LocalDate) {
        changes.deadline = deadline
    }

    fun ownerIdChanged(id: String) {
        changes.ownerId = id
    }

    fun employeesChanged(employees: List<Employee>) {
        changes.employees = employees

        _projectEmployeesLiveData.value = employees.map { EmployeeViewData(it.id, it.fullName, it.position.name) }
    }

    fun saveChanges() {
//        val validationData = getValidationErrorData()
//
//        if (validationData != null) {
//            _validationErrorLiveData.value = validationData
//            return
//        }

        viewModelScope.launchSafeCall(_savingChangesLiveData) {
            val result = safeRequestCall { behaviour.applyChanges(projectsRepository, changes) }
            if (result != null) _closeChangeScreenLiveData.call()
        }
    }

    fun openEmployeesSelection() {
        _openEmployeesSelectionLiveData.value = changes.employees?.map { it.id } ?: emptyList()
    }

//    private fun getValidationErrorData(): CustomerValidationErrorViewData? {
//        val firstNameError = if (changes.firstName.isNullOrBlank()) R.string.first_name_error else null
//        val lastNameError = if (changes.lastName.isNullOrBlank()) R.string.last_name_error else null
//        val countryError = if (changes.country.isNullOrBlank()) R.string.country_error else null
//        val emailError = if (changes.email.isNullOrBlank()) R.string.email_error else null
//        val phoneNumberError = if (changes.phoneNumber.isNullOrBlank()) R.string.phone_number_error else null
//
//        if (firstNameError == null &&
//            lastNameError == null &&
//            countryError == null &&
//            emailError == null &&
//            phoneNumberError == null
//        ) {
//            return null
//        }
//
//        return CustomerValidationErrorViewData(
//            firstNameError?.let { stringProvider.getString(it) },
//            lastNameError?.let { stringProvider.getString(it) },
//            countryError?.let { stringProvider.getString(it) },
//            emailError?.let { stringProvider.getString(it) },
//            phoneNumberError?.let { stringProvider.getString(it) }
//        )
//    }

    private fun formatDeadline(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return date.format(formatter)
    }
}