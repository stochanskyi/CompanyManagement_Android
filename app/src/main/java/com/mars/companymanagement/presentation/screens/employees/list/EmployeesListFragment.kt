package com.mars.companymanagement.presentation.screens.employees.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentEmployeesFragmentBinding
import com.mars.companymanagement.presentation.screens.employees.list.adapter.EmployeesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesListFragment : Fragment(R.layout.fragment_employees_fragment) {
    private val viewModel: EmployeesListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentEmployeesFragmentBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentEmployeesFragmentBinding) {
        binding.employeesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EmployeesAdapter(viewModel::openEmployeeInfo)
        }
    }

    private fun initObservers(binding: FragmentEmployeesFragmentBinding) {
        viewModel.employeesLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.employeesRecyclerView.isVisible = !isLoading
            binding.progressBar.isVisible = isLoading
        }
        viewModel.employeesLiveData.observe(viewLifecycleOwner) { employees ->
            binding.employeesRecyclerView.adapterAction { setItems(employees) }
        }
    }

    private fun RecyclerView.adapterAction(action: EmployeesAdapter.() -> Unit) {
        (adapter as? EmployeesAdapter)?.action()
    }

}