package com.mars.companymanagement.presentation.screens.employeeSelection

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentCustomerSelectionBinding
import com.mars.companymanagement.presentation.screens.customers.list.adapter.CustomersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleEmployeeSelectionFragment : Fragment(R.layout.fragment_customer_selection) {

    companion object {
        const val SELECTED_EMPLOYEE_DATA_KEY = "selected_user_data_key"
    }

    private val viewModel: SingleEmployeeSelectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCustomerSelectionBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentCustomerSelectionBinding) {
        binding.customersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomersAdapter(viewModel::selectCustomer)
        }
    }

    private fun initObservers(binding: FragmentCustomerSelectionBinding) {
        viewModel.employeesLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.employeesLiveData.observe(viewLifecycleOwner) {
            (binding.customersRecyclerView.adapter as? CustomersAdapter)?.setItems(it)
        }
        viewModel.customerSelectedLiveData.observe(viewLifecycleOwner) {
            val navController = Navigation.findNavController(view ?: return@observe)
            navController.previousBackStackEntry?.savedStateHandle?.set(SELECTED_EMPLOYEE_DATA_KEY, it)
            navController.navigateUp()
        }
    }
}