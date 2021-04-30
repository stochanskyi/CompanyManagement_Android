package com.mars.companymanagement.presentation.screens.transactions.create.employeetransaction

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.databinding.FragmentCreateEmployeeTransactionBinding
import com.mars.companymanagement.presentation.screens.employeeSelection.SingleEmployeeSelectionFragment
import com.mars.companymanagement.utils.inputfilter.DecimalDigitsInputFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEmployeeTransactionFragment : Fragment(R.layout.fragment_create_employee_transaction) {
    private val viewModel: CreateEmployeeTransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCreateEmployeeTransactionBinding.bind(view).run {
            initViews(this)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentCreateEmployeeTransactionBinding) {
        binding.amountEditText.filters += DecimalDigitsInputFilter(9, 2)
    }

    private fun initListeners(binding: FragmentCreateEmployeeTransactionBinding) {
        binding.changeUserButton.setOnClickListener {
            Navigation.findNavController(view ?: return@setOnClickListener).navigate(R.id.to_employees_selection)
        }
        binding.amountEditText.doAfterTextChanged { viewModel.amountChanged(it.toString()) }
        binding.createUserButton.setOnClickListener { viewModel.createTransaction() }
    }

    private fun initObservers(binding: FragmentCreateEmployeeTransactionBinding) {
        Navigation.findNavController(requireView()).currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Employee>(SingleEmployeeSelectionFragment.SELECTED_EMPLOYEE_DATA_KEY)
            ?.observe(viewLifecycleOwner) { viewModel.selectedEmployeeChanged(it) }
        viewModel.employeeInfoLiveData.observe(viewLifecycleOwner) {
            binding.nameTextView.isVisible = true
            binding.nameTextView.text = it.name
            binding.positionTextView.isVisible = true
            binding.positionTextView.text = it.position
        }
        viewModel.closeScreenLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigateUp()
        }
    }
}
