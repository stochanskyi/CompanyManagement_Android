package com.mars.companymanagement.presentation.screens.customers.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentCustomersBinding
import com.mars.companymanagement.presentation.screens.customers.list.adapter.CustomersAdapter
import com.mars.companymanagement.presentation.screens.customers.modify.behaviour.CreateCustomerBehaviour
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomersListFragment : Fragment(R.layout.fragment_customers) {
    private val viewModel: CustomersListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCustomersBinding.bind(view).run {
            initViews(this)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentCustomersBinding) {
        binding.customersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomersAdapter(viewModel::openCustomerDetails)
        }
    }

    private fun initListeners(binding: FragmentCustomersBinding) {
        binding.addCustomerButton.setOnClickListener() {
            val action = CustomersListFragmentDirections.toChangeCustomer(CreateCustomerBehaviour())
            Navigation.findNavController(view ?: return@setOnClickListener).navigate(action)
        }
    }

    private fun initObservers(binding: FragmentCustomersBinding) {
        viewModel.customersLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.customersRecyclerView.isVisible = !isLoading
        }
        viewModel.customersLiveData.observe(viewLifecycleOwner) { customers ->
            binding.customersRecyclerView.adapterAction { setItems(customers) }
        }
        viewModel.openCustomerDetailsLiveData.observe(viewLifecycleOwner) { customer ->
            val action = CustomersListFragmentDirections.toCustomerDetails(customer)
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
    }

    private fun RecyclerView.adapterAction(action: CustomersAdapter.() -> Unit) {
        (adapter as? CustomersAdapter)?.action()
    }
}