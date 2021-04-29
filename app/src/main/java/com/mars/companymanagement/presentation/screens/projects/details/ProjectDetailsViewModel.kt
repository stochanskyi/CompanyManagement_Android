package com.mars.companymanagement.presentation.screens.projects.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.network.employees.response.EmployeeResponse
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectEmployee
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.details.models.EmployeeDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.changesalary.params.ChangeSalaryArgs
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
    private val projectsRepository: ProjectsRepository,
    private val employeesRepository: EmployeesRepository
) : BaseViewModel() {

    private val moneyFormatter = DecimalFormat("0.00").apply {
        this.decimalFormatSymbols.currencySymbol = ","
    }

    private val _preliminaryProjectInfoLiveData: MutableLiveData<PreliminaryProjectViewData> =
        MutableLiveData()
    val preliminaryProjectInfoLiveData: LiveData<PreliminaryProjectViewData> =
        _preliminaryProjectInfoLiveData

    private val _projectDetailsLiveData: MutableLiveData<ProjectDetailsViewData> = MutableLiveData()
    val projectDetailsLiveData: LiveData<ProjectDetailsViewData> = _projectDetailsLiveData

    private val _projectDetailsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectDetailsLoadingLiveData: LiveData<Boolean> = _projectDetailsLoadingLiveData

    private val _employeesLiveData: MutableLiveData<List<ProjectEmployeeViewData>> =
        MutableLiveData()
    val employeesLiveData: LiveData<List<ProjectEmployeeViewData>> = _employeesLiveData

    private val _openCustomerDetailsLiveData: MutableLiveData<Customer> = SingleLiveData()
    val openCustomerDetailsLiveData: LiveData<Customer> = _openCustomerDetailsLiveData

    private val _openEditProjectLiveData: MutableLiveData<ProjectDetails> = SingleLiveData()
    val openEditProjectLiveData: LiveData<ProjectDetails> = _openEditProjectLiveData

    private val _openChangeSalary: MutableLiveData<ChangeSalaryArgs> = SingleLiveData()
    val openChangeSalary: LiveData<ChangeSalaryArgs> = _openChangeSalary

    private val _updateEmployeeSalary: MutableLiveData<Pair<String, String>> = SingleLiveData()
    val updateEmployeeSalary: LiveData<Pair<String, String>> = _updateEmployeeSalary

    private var pendingChangeEmployeeSalary: ProjectEmployee? = null

    private lateinit var projectDetails: ProjectDetails

    init {
        viewModelScope.launch {
            projectsRepository.projectUpdatedFlow.collect {
                if (projectDetails.id != it.id) return@collect

                projectDetails = it
                _preliminaryProjectInfoLiveData.value =
                    PreliminaryProjectViewData(projectDetails.name, projectDetails.status.name)
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
        val employee = projectDetails.employees.firstOrNull { it.id == id } ?: return
        pendingChangeEmployeeSalary = employee

        _openChangeSalary.value =
            ChangeSalaryArgs(employee.fullName, employee.position.name, employee.salary ?: 0f)
    }

    fun salaryChanged(salary: Float) {
        //TODO
        val safePendingChangeEmployeeSalary = pendingChangeEmployeeSalary ?: return

        requestChangeSalary(salary)

        _updateEmployeeSalary.value = Pair(safePendingChangeEmployeeSalary.id, moneyFormatter.format(salary))

        pendingChangeEmployeeSalary = null
    }

    private fun requestChangeSalary(salary: Float) {
        viewModelScope.launch {
            employeesRepository.setSalary(pendingChangeEmployeeSalary!!.id, projectDetails.id, salary)
        }
    }

    fun changeSalaryDismissed() {
        pendingChangeEmployeeSalary = null
    }

    private fun Employee.toViewData() = EmployeeDetailsViewData(fullName, position.name, email)

    private fun Project.toViewData() = PreliminaryProjectViewData(name, status.name)

    private fun ProjectDetails.toViewData() = ProjectDetailsViewData(
        formatDeadline(deadline),
        description,
        moneyFormatter.format(budget),
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