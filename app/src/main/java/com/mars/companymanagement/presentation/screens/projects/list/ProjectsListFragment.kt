package com.mars.companymanagement.presentation.screens.projects.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentProjectsBinding
import com.mars.companymanagement.presentation.screens.projects.list.adapter.ProjectsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectsListFragment : Fragment(R.layout.fragment_projects) {
    private val viewModel: ProjectsListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentProjectsBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentProjectsBinding) {
        binding.projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ProjectsAdapter(viewModel::openProjectDetails)
        }
    }

    private fun initObservers(binding: FragmentProjectsBinding) {
        viewModel.projectsLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.projectsRecyclerView.isVisible = !isLoading
        }
        viewModel.projectsLiveData.observe(viewLifecycleOwner) { projects ->
            binding.projectsRecyclerView.adapterAction { setItems(projects) }
        }
    }

    private fun RecyclerView.adapterAction(action: ProjectsAdapter.() -> Unit) {
        (adapter as? ProjectsAdapter)?.action()
    }
}