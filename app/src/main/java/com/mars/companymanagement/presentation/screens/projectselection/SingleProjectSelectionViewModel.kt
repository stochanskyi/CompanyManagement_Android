package com.mars.companymanagement.presentation.screens.projectselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.list.models.ItemViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleProjectSelectionViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository
) : BaseViewModel() {

    private val _projectsLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val projectsLoadingLiveData: LiveData<Boolean> = _projectsLoadingLiveData

    private val _projectsLiveData: MutableLiveData<List<ItemViewData>> = MutableLiveData()
    val projectsLiveData: LiveData<List<ItemViewData>> = _projectsLiveData

    private val _projectSelectedLiveData: MutableLiveData<Project> = MutableLiveData()
    val projectSelectedLiveData: LiveData<Project> = _projectSelectedLiveData

    private lateinit var projects: List<Project>

    init {
        loadProjects()
    }

    fun selectProject(id: String) {
        val project = projects.firstOrNull { it.id == id } ?: return
        _projectSelectedLiveData.value = project
    }

    private fun loadProjects() {
        viewModelScope.launchSafeCall(_projectsLoadingLiveData) {
            projects =
                safeRequestCall { projectsRepository.getProjects() } ?: return@launchSafeCall

            _projectsLiveData.value =
                projects.map { ItemViewData(it.id, it.name, it.status.name) }
        }
    }
}