package com.mars.companymanagement.presentation.screens.projects.modify

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.databinding.FragmentChangeProjectBinding
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import com.mars.companymanagement.presentation.screens.projects.customerselection.CustomerSelectionFragment
import com.mars.companymanagement.presentation.screens.projects.employeeselection.EmployeeSelectionFragment
import com.mars.companymanagement.presentation.screens.projects.modify.adapter.ProjectEmployeesAdapter
import com.mars.companymanagement.presentation.screens.projects.modify.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.views.IdentifiableArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class ChangeProjectFragment : Fragment(R.layout.fragment_change_project), ToolbarMenuListener {
    private val viewModel: ChangeProjectViewModel by viewModels()
    private val args: ChangeProjectFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.behaviour)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentChangeProjectBinding.bind(view).run {
            setupToolbar()
            initViews(this)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentChangeProjectBinding) {
        binding.employeesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ProjectEmployeesAdapter()
        }
    }

    private fun setupToolbar() {
        (activity as? ToolbarConfigurator)?.apply {
            modifyToolbarConfiguration {
                menuId = R.menu.menu_save_changes
            }
            setMenuItemsListener(viewLifecycleOwner, this@ChangeProjectFragment)
        }
    }

    private fun initListeners(binding: FragmentChangeProjectBinding) {
        binding.nameEditText.doAfterTextChanged {
            binding.nameLayout.isErrorEnabled = false
            viewModel.nameChanged(it.toString())
        }
        binding.descriptionEditText.doAfterTextChanged {
            binding.deadlineLayout.isErrorEnabled = false
            viewModel.descriptionChanged(it.toString())
        }
        binding.editEmployeesButton.setOnClickListener { viewModel.openEmployeesSelection() }

        binding.customerNameEditText.setOnClickListener { viewModel.openCustomerSelection() }
    }

    private fun initObservers(binding: FragmentChangeProjectBinding) {
        initSaveStateHandleObservers(binding)

        viewModel.preliminaryCustomerLiveData.observe(viewLifecycleOwner) {
            setPreliminaryData(binding, it)
        }
        viewModel.closeChangeScreenLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigateUp()
        }
        viewModel.validationErrorLiveData.observe(viewLifecycleOwner) {
            //TODO
        }
        viewModel.savingChangesLiveData.observe(viewLifecycleOwner) {
            //TODO
        }
        viewModel.projectStatusesLiveData.observe(viewLifecycleOwner) {
            binding.dropdown.setAdapter(
                ArrayAdapter(requireContext(), R.layout.list_item, it)
            )
        }
        viewModel.projectEmployeesLiveData.observe(viewLifecycleOwner) {
            binding.employeesRecyclerView.adapterAction { setItems(it) }
        }
        viewModel.openEmployeesSelectionLiveData.observe(viewLifecycleOwner) {
            val action = ChangeProjectFragmentDirections.toEmployeesSelection(it.toTypedArray())
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
        viewModel.openCustomerSelectionLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigate(R.id.to_customer_selection)
        }
    }

    private fun initSaveStateHandleObservers(binding: FragmentChangeProjectBinding) {
        val navController = Navigation.findNavController(requireView())

        navController.currentBackStackEntry?.savedStateHandle
            ?.getLiveData<List<Employee>>(EmployeeSelectionFragment.SELECTED_EMPLOYEES_DATA_KEY)
            ?.observe(viewLifecycleOwner, viewModel::employeesChanged)

        navController.currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Customer>(CustomerSelectionFragment.SELECTED_USER_DATA_KEY)
            ?.observe(viewLifecycleOwner) {
                binding.customerNameEditText.setText(it.fullName)
                viewModel.ownerIdChanged(it.id)
            }
    }

//    private fun showValidationErrors(binding: FragmentChangeProjectBinding, data: CustomerValidationErrorViewData) {
//        binding.apply {
//            data.firstNameValidationError?.let { binding.firstNameLayout.error = it }
//            data.lastNameValidationError?.let { binding.lastNameLayout.error = it }
//            data.countryValidationError?.let { binding.countryLayout.error = it }
//            data.emailValidationError?.let { binding.emailLayout.error = it }
//            data.phoneNumberValidationError?.let { binding.phoneNumberEditText.error = it }
//        }
//    }

    private fun setPreliminaryData(binding: FragmentChangeProjectBinding, data: PreliminaryProjectViewData) {
        binding.apply {
            nameEditText.setText(data.name)
            descriptionEditText.setText(data.description)
            deadlineEditText.setText(data.deadline)
            customerNameEditText.setText(data.customerName)
            dropdown.setText(data.status)
        }
    }

    private fun RecyclerView.adapterAction(action: ProjectEmployeesAdapter.() -> Unit) {
        (adapter as? ProjectEmployeesAdapter)?.action()
    }

    override fun onItemSelected(itemId: Int) {
        if (itemId == R.id.save_changes_action) viewModel.saveChanges()
    }
}