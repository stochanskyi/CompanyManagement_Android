package com.mars.companymanagement.presentation.screens.employees.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentEmployeeDetailsBinding
import com.mars.companymanagement.presentation.screens.employees.modify.behaviour.EditEmployeeBehaviour
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import com.mars.companymanagement.presentation.screens.projects.list.adapter.ProjectsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeDetailsFragment : Fragment(R.layout.fragment_employee_details), ToolbarMenuListener {
    private val args: EmployeeDetailsFragmentArgs by navArgs()
    private val viewModel: EmployeeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.employee)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentEmployeeDetailsBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentEmployeeDetailsBinding) {
        changeToolbar()
        binding.projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ProjectsAdapter(viewModel::openProjectDetails)
        }
    }

    private fun initObservers(binding: FragmentEmployeeDetailsBinding) {
        viewModel.projectsLiveData.observe(viewLifecycleOwner) {
            binding.projectsRecyclerView.adapterAction { setItems(it) }
        }
        viewModel.projectsLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.projectsRecyclerView.isVisible = !it
        }
        viewModel.employeeInfoViewData.observe(viewLifecycleOwner) {
            (activity as? ToolbarConfigurator)?.run {
                modifyToolbarConfiguration {
                    name = it.name
                }
            }
            binding.nameTextView.text = it.name
            binding.positionTextView.text = it.position
            binding.emailTextView.text = it.email
        }
        viewModel.openProjectDetailsLiveData.observe(viewLifecycleOwner) {
            val action = EmployeeDetailsFragmentDirections.toProjectDetails(it)
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
        viewModel.openEditEmployeeLiveData.observe(viewLifecycleOwner) {
            val action = EmployeeDetailsFragmentDirections.toChangeEmployee(EditEmployeeBehaviour(it))
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
    }

    private fun changeToolbar() {
        (activity as? ToolbarConfigurator)?.apply {
            modifyToolbarConfiguration { menuId = R.menu.menu_edit_item }
            setMenuItemsListener(viewLifecycleOwner, this@EmployeeDetailsFragment)
        }
    }

    private fun RecyclerView.adapterAction(action: ProjectsAdapter.() -> Unit) {
        (adapter as? ProjectsAdapter)?.action()
    }

    override fun onItemSelected(itemId: Int) {
        if (itemId == R.id.edit_item_action) viewModel.openEditEmployee()
    }
}