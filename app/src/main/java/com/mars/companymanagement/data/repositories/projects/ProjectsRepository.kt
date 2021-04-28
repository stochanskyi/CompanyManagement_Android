package com.mars.companymanagement.data.repositories.projects

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.customers.response.response.CustomerResponse
import com.mars.companymanagement.data.network.employees.response.EmployeeResponse
import com.mars.companymanagement.data.network.projects.ProjectsDataSource
import com.mars.companymanagement.data.network.projects.request.CreateProjectRequest
import com.mars.companymanagement.data.network.projects.request.UpdateProjectRequest
import com.mars.companymanagement.data.network.projects.response.ProjectDetailsResponse
import com.mars.companymanagement.data.network.projects.response.ProjectResponse
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.projects.models.details.CreateProjectData
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.data.repositories.projects.models.details.UpdateProjectData
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.data.repositories.projects.models.info.ProjectStatus
import com.mars.companymanagement.ext.withIoContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface ProjectsRepository {
    suspend fun getProjects(): RequestResult<List<Project>>

    suspend fun getProjectsForEmployee(employeeId: String): RequestResult<List<Project>>

    suspend fun getProjectDetails(projectId: String): RequestResult<ProjectDetails>

    suspend fun getCustomerProjects(customerId: String): RequestResult<List<Project>>

    suspend fun updateProject(date: UpdateProjectData): RequestResult<ProjectDetails>

    suspend fun createProject(date: CreateProjectData): RequestResult<ProjectDetails>
}

private const val DATE_SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
private const val SERVER_ZONE_ID = "UTC"

class ProjectsRepositoryImpl @Inject constructor(
    private val projectsDataSource: ProjectsDataSource
) : ProjectsRepository {

    override suspend fun getProjects(): RequestResult<List<Project>> = withIoContext {
        projectsDataSource.getProjects().map { it.parse() }
    }

    override suspend fun getProjectsForEmployee(employeeId: String): RequestResult<List<Project>> =
        withIoContext { projectsDataSource.getProjectsForEmployee(employeeId.toInt()).map { it.parse() } }

    override suspend fun getProjectDetails(projectId: String): RequestResult<ProjectDetails> =
        withIoContext { projectsDataSource.getProjectDetails(projectId.toInt()).map { it.parse() } }

    override suspend fun getCustomerProjects(customerId: String): RequestResult<List<Project>> =
        withIoContext { projectsDataSource.getCustomerProjects(customerId.toInt()).map { it.parse() } }

    override suspend fun updateProject(date: UpdateProjectData): RequestResult<ProjectDetails> =
        withIoContext { projectsDataSource.updateProject(date.createRequest()) }.map { it.parse() }

    override suspend fun createProject(date: CreateProjectData): RequestResult<ProjectDetails> =
        withIoContext { projectsDataSource.createProject(date.createRequest()) }.map { it.parse() }

    private fun List<ProjectResponse>.parse() = map { it.parse() }

    private fun ProjectResponse.parse() = Project(id, name, status.parse())

    private fun ProjectResponse.ProjectStatusResponse.parse() = ProjectStatus(id, name)

    private fun ProjectDetailsResponse.parse() = ProjectDetails(
        id = id.toString(),
        name = name,
        description = description,
        budget = budget,
        deadline = parseDate(deadline),
        projectOwner = customer.parse(),
        status = status.parse(),
        employees = employees.map { it.parse() }
    )

    private fun CustomerResponse.parse() = Customer(
        id.toString(),
        firstName,
        lastName,
        country,
        email,
        phoneNumber
    )

    private fun parseDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(DATE_SERVER_PATTERN).withZone(ZoneId.of(SERVER_ZONE_ID))
        return ZonedDateTime.parse(date, formatter)
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDate()
    }

    private fun EmployeeResponse.parse() =
        Employee(
            id,
            firstName,
            lastName,
            email,
            position.parse()
        )

    private fun EmployeeResponse.EmployeePositionResponse.parse() = EmployeePosition(id, name)

    private fun UpdateProjectData.createRequest() = UpdateProjectRequest(
        projectId.toInt(),
        name,
        description,
        customerId.toInt(),
        statusId.toInt(),
        createTimestamp(deadline),
        employeeIds.map { it.toInt() }
    )

    private fun CreateProjectData.createRequest() = CreateProjectRequest(
        name,
        description,
        customerId.toInt(),
        statusId.toInt(),
        createTimestamp(deadline),
        employeeIds.map { it.toInt() }
    )

    private fun createTimestamp(date: LocalDate): String = LocalDateTime.of(date, LocalTime.now()).format(DateTimeFormatter.ofPattern(DATE_SERVER_PATTERN))
}