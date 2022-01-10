package com.mars.companymanagement.data.repositories.taxonomies

import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.projects.models.info.ProjectStatus
import com.mars.companymanagement.data.storages.taxonomy.TaxonomyStorage
import javax.inject.Inject

interface TaxonomyRepository {
    suspend fun getPositions(): List<EmployeePosition>
    suspend fun getProjectStatuses(): List<ProjectStatus>
}

class TaxonomyRepositoryImpl @Inject constructor(
    private val taxonomyStorage: TaxonomyStorage
) : TaxonomyRepository {
    override suspend fun getPositions(): List<EmployeePosition> =
        taxonomyStorage.getPositions().map { EmployeePosition(it.id, it.name) }

    override suspend fun getProjectStatuses(): List<ProjectStatus> =
        taxonomyStorage.getProjectStatuses().map { ProjectStatus(it.id, it.name) }
}