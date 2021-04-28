package com.mars.companymanagement.presentation.screens.projects.modify

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentChangeProjectBinding
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import com.mars.companymanagement.presentation.screens.projects.modify.models.PreliminaryProjectViewData
import com.mars.companymanagement.presentation.views.IdentifiableArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun initObservers(binding: FragmentChangeProjectBinding) {
        viewModel.preliminaryCustomerLiveData.observe(viewLifecycleOwner) {
            setPreliminaryData(binding, it)
        }
        viewModel.closeChangeScreenLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigateUp()
        }
        viewModel.validationErrorLiveData.observe(viewLifecycleOwner) {
//            showValidationErrors(binding, it)
        }
        viewModel.savingChangesLiveData.observe(viewLifecycleOwner) {
        }
        viewModel.projectStatusesLiveData.observe(viewLifecycleOwner) {
            binding.dropdown.setAdapter(
                ArrayAdapter(requireContext(), R.layout.list_item, it)
            )
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

    override fun onItemSelected(itemId: Int) {
        if (itemId == R.id.save_changes_action) viewModel.saveChanges()
    }
}