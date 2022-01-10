package com.mars.companymanagement.data.repositories.taxonomies

import com.mars.companymanagement.data.database.feature.taxonomy.RoomTaxonomyDataSource
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.projects.models.info.ProjectStatus
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

class RoomTaxonomyRepositoryImpl @Inject constructor(
    private val taxonomyDataSource: RoomTaxonomyDataSource
) : TaxonomyRepository {

    override suspend fun getPositions(): List<EmployeePosition> = withIoContext {
        taxonomyDataSource.getPositions().map { EmployeePosition(it.positionId.toString(), it.name) }
    }

    override suspend fun getProjectStatuses(): List<ProjectStatus> = withIoContext {
        taxonomyDataSource.getProjectStatuses().map { ProjectStatus(it.statusId.toString(), it.name) }
    }

}