package com.mars.companymanagement.presentation.screens.projectselection

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
class SingleProjectSelectionFragment : Fragment(R.layout.fragment_customer_selection) {

    companion object {
        const val SELECTED_PROJECT_DATA_KEY = "selected_project_data_key"
    }

    private val viewModel: SingleProjectSelectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCustomerSelectionBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentCustomerSelectionBinding) {
        binding.customersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomersAdapter(viewModel::selectProject)
        }
    }

    private fun initObservers(binding: FragmentCustomerSelectionBinding) {
        viewModel.projectsLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.projectsLiveData.observe(viewLifecycleOwner) {
            (binding.customersRecyclerView.adapter as? CustomersAdapter)?.setItems(it)
        }
        viewModel.projectSelectedLiveData.observe(viewLifecycleOwner) {
            val navController = Navigation.findNavController(view ?: return@observe)
            navController.previousBackStackEntry?.savedStateHandle?.set(SELECTED_PROJECT_DATA_KEY, it)
            navController.navigateUp()
        }
    }
}