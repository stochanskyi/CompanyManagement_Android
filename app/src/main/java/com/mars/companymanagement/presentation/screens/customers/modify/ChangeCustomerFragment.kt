package com.mars.companymanagement.presentation.screens.customers.modify

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentChangeCustomerBinding
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerValidationErrorViewData
import com.mars.companymanagement.presentation.screens.customers.modify.models.PreliminaryCustomerViewData
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeCustomerFragment : Fragment(R.layout.fragment_change_customer), ToolbarMenuListener {
    val viewModel: ChangeCustomerViewModel by viewModels()
    val args: ChangeCustomerFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.behaviour)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentChangeCustomerBinding.bind(view).apply {
            setupToolbar()
            initListeners(this)
            initObservers(this)
        }
    }

    private fun setupToolbar() {
        (activity as? ToolbarConfigurator)?.apply {
            modifyToolbarConfiguration {
                menuId = R.menu.menu_save_changes
            }
            setMenuItemsListener(viewLifecycleOwner, this@ChangeCustomerFragment)
        }
    }

    private fun initListeners(binding: FragmentChangeCustomerBinding) {
        binding.firstNameEditText.doAfterTextChanged {
            binding.firstNameLayout.isErrorEnabled = false
            viewModel.firstNameChanged(it.toString())
        }
        binding.lastNameEditText.doAfterTextChanged {
            binding.lastNameLayout.isErrorEnabled = false
            viewModel.lastNameChanged(it.toString())
        }
        binding.countryEditText.doAfterTextChanged {
            binding.countryLayout.isErrorEnabled = false
            viewModel.countryChanged(it.toString())
        }
        binding.emailEditText.doAfterTextChanged {
            binding.emailLayout.isErrorEnabled = false
            viewModel.emailChanged(it.toString())
        }
        binding.phoneNumberEditText.doAfterTextChanged {
            binding.phoneNumberLayout.isErrorEnabled = false
            viewModel.phoneNumberChanged(it.toString())
        }
    }

    private fun initObservers(binding: FragmentChangeCustomerBinding) {
        viewModel.preliminaryCustomerLiveData.observe(viewLifecycleOwner) {
            setPreliminaryData(binding, it)
        }
        viewModel.closeChangeScreenLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigateUp()
        }
        viewModel.validationErrorLiveData.observe(viewLifecycleOwner) {
            showValidationErrors(binding, it)
        }
        viewModel.savingChangesLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
    }

    private fun showValidationErrors(binding: FragmentChangeCustomerBinding, data: CustomerValidationErrorViewData) {
        binding.apply {
            data.firstNameValidationError?.let { binding.firstNameLayout.error = it }
            data.lastNameValidationError?.let { binding.lastNameLayout.error = it }
            data.countryValidationError?.let { binding.countryLayout.error = it }
            data.emailValidationError?.let { binding.emailLayout.error = it }
            data.phoneNumberValidationError?.let { binding.phoneNumberEditText.error = it }
        }
    }

    private fun setPreliminaryData(binding: FragmentChangeCustomerBinding, data: PreliminaryCustomerViewData) {
        binding.apply {
            firstNameEditText.setText(data.firstName)
            lastNameEditText.setText(data.lastName)
            countryEditText.setText(data.country)
            emailEditText.setText(data.email)
            phoneNumberEditText.setText(data.phoneNumber)
        }
    }

    override fun onItemSelected(itemId: Int) {
        if (itemId == R.id.save_changes_action) viewModel.saveChanges()
    }
}