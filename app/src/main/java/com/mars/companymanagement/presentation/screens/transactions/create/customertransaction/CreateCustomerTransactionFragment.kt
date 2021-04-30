package com.mars.companymanagement.presentation.screens.transactions.create.customertransaction

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import com.mars.companymanagement.databinding.FragmentCreateCustomerTransactionBinding
import com.mars.companymanagement.presentation.screens.projects.customerselection.CustomerSelectionFragment
import com.mars.companymanagement.presentation.screens.projectselection.SingleProjectSelectionFragment
import com.mars.companymanagement.utils.inputfilter.DecimalDigitsInputFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCustomerTransactionFragment : Fragment(R.layout.fragment_create_customer_transaction) {
    private val viewModel: CreateCustomerTransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCreateCustomerTransactionBinding.bind(view).run {
            initViews(this)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentCreateCustomerTransactionBinding) {
        binding.amountEditText.filters += DecimalDigitsInputFilter(9, 2)
    }

    private fun initListeners(binding: FragmentCreateCustomerTransactionBinding) {
        binding.changeCustomerButton.setOnClickListener {
            Navigation.findNavController(view ?: return@setOnClickListener).navigate(R.id.to_customer_selection)
        }
        binding.changeProjectButton.setOnClickListener {
            Navigation.findNavController(view ?: return@setOnClickListener).navigate(R.id.to_project_selection)
        }
        binding.amountEditText.doAfterTextChanged { viewModel.amountChanged(it.toString()) }
        binding.createTransactionButton.setOnClickListener { viewModel.createTransaction() }
    }

    private fun initObservers(binding: FragmentCreateCustomerTransactionBinding) {
        Navigation.findNavController(requireView()).currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Customer>(CustomerSelectionFragment.SELECTED_USER_DATA_KEY)
            ?.observe(viewLifecycleOwner) { viewModel.selectedCustomerChanged(it) }
        Navigation.findNavController(requireView()).currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Project>(SingleProjectSelectionFragment.SELECTED_PROJECT_DATA_KEY)
            ?.observe(viewLifecycleOwner) { viewModel.selectedProjectChanged(it) }

        viewModel.customerInfoLiveData.observe(viewLifecycleOwner) {
            binding.customerNameTextView.isVisible = true
            binding.customerNameTextView.text = it.name
            binding.countryTextView.isVisible = true
            binding.countryTextView.text = it.country
        }

        viewModel.projectInfoLiveData.observe(viewLifecycleOwner) {
            binding.projectNameTextView.isVisible = true
            binding.projectNameTextView.text = it.name
            binding.projectStatusTextView.isVisible = true
            binding.projectStatusTextView.text = it.status
        }
        viewModel.closeScreenLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigateUp()
        }
    }
}