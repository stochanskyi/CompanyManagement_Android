package com.mars.companymanagement.data.database.feature.taxonomy.project

import javax.inject.Inject

interface ProjectStatusesDataSource {
    suspend fun getProjectStatuses(): List<ProjectStatusEntity>
}

class ProjectStatusesDataSourceImpl @Inject constructor(
    private val projectStatusDao: ProjectStatusDao
) : ProjectStatusesDataSource {

    override suspend fun getProjectStatuses(): List<ProjectStatusEntity> {
        return projectStatusDao.getProjectStatuses()
    }

}