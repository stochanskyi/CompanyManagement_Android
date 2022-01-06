package com.mars.companymanagement.presentation.screens.projects.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentProjectDetailsBinding
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import com.mars.companymanagement.presentation.screens.projects.changesalary.ChangeSalaryDialog
import com.mars.companymanagement.presentation.screens.projects.changesalary.ChangeSalaryListener
import com.mars.companymanagement.presentation.screens.projects.details.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectDetailsViewData
import com.mars.companymanagement.presentation.screens.projects.modify.behaviour.EditProjectBehaviour
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectDetailsFragment : Fragment(R.layout.fragment_project_details), ToolbarMenuListener, ChangeSalaryListener {
    private val viewModel: ProjectDetailsViewModel by viewModels()
    private val args: ProjectDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.preliminaryProject)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as? ToolbarConfigurator)?.apply {
            modifyToolbarConfiguration { menuId = R.menu.menu_edit_item }
            setMenuItemsListener(viewLifecycleOwner, this@ProjectDetailsFragment)
        }
        FragmentProjectDetailsBinding.bind(view).run {
            initViews(this)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentProjectDetailsBinding) {
        binding.employeesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ProjectEmployeeAdapter(viewModel::employeeSelected)
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
        viewModel.employeesLiveData.observe(viewLifecycleOwner) {
            binding.employeeTextView.isVisible = true
            (binding.employeesRecyclerView.adapter as? ProjectEmployeeAdapter)?.setItems(it)
        }
        viewModel.openCustomerDetailsLiveData.observe(viewLifecycleOwner) {
            val action = ProjectDetailsFragmentDirections.toCustomerDetails(it)
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
        viewModel.openEditProjectLiveData.observe(viewLifecycleOwner) {
            //TODO Fix navigation graph
//            val action = ProjectDetailsFragmentDirections.toChangeProject(EditProjectBehaviour(it))
//            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
        viewModel.openChangeSalary.observe(viewLifecycleOwner) {
            ChangeSalaryDialog.newInstance(it).show(childFragmentManager, "")
        }
        viewModel.updateEmployeeSalary.observe(viewLifecycleOwner) {
            (binding.employeesRecyclerView.adapter as? ProjectEmployeeAdapter)?.setSalary(it.first, it.second)
        }
    }

    private fun setPreliminaryProjectInfo(binding: FragmentProjectDetailsBinding, preliminaryInfo: PreliminaryProjectViewData) = binding.run {
        (activity as? ToolbarConfigurator)?.modifyToolbarConfiguration { name = preliminaryInfo.name }
        nameTextView.text = preliminaryInfo.name
        statusTextView.text = preliminaryInfo.status
    }

    private fun setProjectDetails(binding: FragmentProjectDetailsBinding, projectDetails: ProjectDetailsViewData) {
        binding.run {
            budgetTextView.text = getString(R.string.budget_format, projectDetails.budget)

            deadlineTextView.text = getString(R.string.project_complete_until, projectDetails.deadline)
            descriptionTextView.text = projectDetails.description

            customerTextView.isVisible = true
            customerInfoLayout.isVisible = true

            customerNameTextView.text = projectDetails.ownerName
            customerCountryTextView.text = projectDetails.ownerCountry
        }
    }

    override fun onItemSelected(itemId: Int) {
        if (itemId == R.id.edit_item_action) viewModel.editProject()
    }

    override fun salaryChanged(salary: Float) {
        viewModel.salaryChanged(salary)
    }

    override fun salaryChangeDismissed() {
        viewModel.changeSalaryDismissed()
    }
}