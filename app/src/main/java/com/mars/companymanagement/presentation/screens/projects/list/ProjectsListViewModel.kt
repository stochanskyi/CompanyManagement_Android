package com.mars.companymanagement.presentation.screens.projects.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.Project
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {

    private val _projectsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectsLoadingLiveData: LiveData<Boolean> = _projectsLoadingLiveData

    private val _projectsLiveData: MutableLiveData<List<ProjectViewData>> = MutableLiveData()
    val projectsLiveData: LiveData<List<ProjectViewData>> = _projectsLiveData

    private var projects: List<Project> = emptyList()

    init {
        loadProjects()
    }

    fun openProjectDetails(projectId: String) {
        //TODO
    }

    private fun loadProjects() {
        viewModelScope.launch {
            projects = safeRequestCallWithLoading(_projectsLoadingLiveData) {
                projectsRepository.getProjects()
            } ?: return@launch
            _projectsLiveData.value = projects.map { it.toViewData() }
        }
    }

    private fun Project.toViewData() = ProjectViewData(id, name, status.name)

}