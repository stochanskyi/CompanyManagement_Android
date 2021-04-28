package com.mars.companymanagement.presentation.screens.employees.modify

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentChangeEmployeeBinding
import com.mars.companymanagement.presentation.screens.employees.modify.models.PreliminaryEmployeeViewData
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import com.mars.companymanagement.presentation.views.IdentifiableAdapterItem
import com.mars.companymanagement.presentation.views.IdentifiableArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeEmployeeFragment : Fragment(R.layout.fragment_change_employee), ToolbarMenuListener {
    private val args: ChangeEmployeeFragmentArgs by navArgs()
    private val viewModel: ChangeEmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.behaviour)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentChangeEmployeeBinding.bind(view).apply {
            setupToolbar()
            initViews(this)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun setupToolbar() {
        (activity as? ToolbarConfigurator)?.apply {
            modifyToolbarConfiguration {
                menuId = R.menu.menu_save_changes
            }
            setMenuItemsListener(viewLifecycleOwner, this@ChangeEmployeeFragment)
        }
    }

    private fun initViews(binding: FragmentChangeEmployeeBinding) {
        binding.dropdown.doAfterTextChanged {
            viewModel.positionChanged(it.toString())
        }
    }

    private fun initListeners(binding: FragmentChangeEmployeeBinding) {
        binding.firstNameEditText.doAfterTextChanged { viewModel.firstNameChanged(it.toString()) }
        binding.lastNameEditText.doAfterTextChanged { viewModel.lastNameChanged(it.toString()) }
        binding.emailEditText.doAfterTextChanged { viewModel.emailChanged(it.toString()) }
    }

    private fun initObservers(binding: FragmentChangeEmployeeBinding) {
        viewModel.positionsLiveData.observe(viewLifecycleOwner) {
            setItems(binding, it)
        }
        viewModel.preliminaryEmployeeLiveData.observe(viewLifecycleOwner) {
            setPreliminaryData(binding, it)
        }
        viewModel.closeChangeScreenLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigateUp()
        }
    }

    private fun setPreliminaryData(binding: FragmentChangeEmployeeBinding, data: PreliminaryEmployeeViewData) {
        binding.apply {
            firstNameEditText.setText(data.firstName)
            lastNameEditText.setText(data.lastName)
            emailEditText.setText(data.email)
            dropdown.setText(data.position)
        }
    }

    private fun setItems(binding: FragmentChangeEmployeeBinding, items: List<IdentifiableAdapterItem>) {
        binding.dropdown.setAdapter(
            IdentifiableArrayAdapter(requireContext(), R.layout.list_item, items)
        )
    }

    override fun onItemSelected(itemId: Int) {
        if (itemId == R.id.save_changes_action) viewModel.saveChanges()
    }
}