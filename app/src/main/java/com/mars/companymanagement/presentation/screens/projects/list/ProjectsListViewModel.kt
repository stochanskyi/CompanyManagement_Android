package com.mars.companymanagement.presentation.screens.projects.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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

    private val _openProjectDetailsLiveData: MutableLiveData<Project> = SingleLiveData()
    val openProjectDetailsLiveData: LiveData<Project> = _openProjectDetailsLiveData

    private val projects: MutableList<Project> = mutableListOf()

    init {
        viewModelScope.launch {
            projectsRepository.projectUpdatedFlow.collect { changedProject ->
                val projectIndex = projects.indexOfFirst { it.id == changedProject.id }.takeIf { it >= 0 } ?: return@collect
                val newProject = projects[projectIndex].copy(
                    name = changedProject.name,
                    status = changedProject.status
                )

                projects.apply {
                    removeAt(projectIndex)
                    add(projectIndex, newProject)
                }

                _projectsLiveData.value = projects.map { it.toViewData() }
            }
        }

        viewModelScope.launch {
            projectsRepository.projectCreatedFlow.collect {
                val newProject = Project(it.id, it.name, it.status)

                projects += newProject

                _projectsLiveData.value = projects.map { it.toViewData() }
            }
        }

        loadProjects()
    }

    fun openProjectDetails(projectId: String) {
        _openProjectDetailsLiveData.value = projects.firstOrNull { it.id == projectId } ?: return
    }

    private fun loadProjects() {
        viewModelScope.launch {
            val loadedProjects = safeRequestCallWithLoading(_projectsLoadingLiveData) {
                projectsRepository.getProjects()
            } ?: return@launch

            projects.apply {
                clear()
                addAll(loadedProjects)
            }

            _projectsLiveData.value = projects.map { it.toViewData() }
        }
    }

    private fun Project.toViewData() = ProjectViewData(id, name, status.name)

}