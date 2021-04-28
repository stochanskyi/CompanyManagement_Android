package com.mars.companymanagement.presentation.screens.projects.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentProjectDetailsBinding
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.projects.details.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectDetailsViewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectDetailsFragment : Fragment(R.layout.fragment_project_details) {
    private val viewModel: ProjectDetailsViewModel by viewModels()
    private val args: ProjectDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.preliminaryProject)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentProjectDetailsBinding.bind(view).run {
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initListeners(binding: FragmentProjectDetailsBinding) {
        binding.customerInfoLayout.setOnClickListener { viewModel.openCustomerDetails() }
    }

    private fun initObservers(binding: FragmentProjectDetailsBinding) {
        viewModel.projectDetailsLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.preliminaryProjectInfoLiveData.observe(viewLifecycleOwner) {
            setPreliminaryProjectInfo(binding, it)
        }
        viewModel.projectDetailsLiveData.observe(viewLifecycleOwner) {
            setProjectDetails(binding, it)
        }
        viewModel.openCustomerDetailsLiveData.observe(viewLifecycleOwner) {
            val action = ProjectDetailsFragmentDirections.toCustomerDetails(it)
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
    }

    private fun setPreliminaryProjectInfo(binding: FragmentProjectDetailsBinding, preliminaryInfo: PreliminaryProjectViewData) = binding.run {
        (activity as? ToolbarConfigurator)?.modifyToolbarConfiguration { name = preliminaryInfo.name }
        nameTextView.text = preliminaryInfo.name
        statusTextView.text = preliminaryInfo.status
    }

    private fun setProjectDetails(binding: FragmentProjectDetailsBinding, projectDetails: ProjectDetailsViewData) {
        binding.run {
            deadlineTextView.text = getString(R.string.project_complete_until, projectDetails.deadline)
            descriptionTextView.text = projectDetails.description

            customerTextView.isVisible = true
            customerInfoLayout.isVisible = true

            customerNameTextView.text = projectDetails.ownerName
            customerCountryTextView.text = projectDetails.ownerCountry
        }
    }
}