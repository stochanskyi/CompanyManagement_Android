package com.mars.companymanagement.presentation.screens.projects.employeeselection

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentEmployeesSelectionBinding
import com.mars.companymanagement.presentation.screens.projects.employeeselection.adapter.SelectableEmployeeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeSelectionFragment : Fragment(R.layout.fragment_employees_selection) {

    companion object {
        const val SELECTED_EMPLOYEES_DATA_KEY = "selected_employees_data_key"
    }

    private val viewModel: EmployeeSelectionViewModel by viewModels()
    private val args: EmployeeSelectionFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.selectedEmployees.asList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentEmployeesSelectionBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentEmployeesSelectionBinding) {
        binding.employeesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectableEmployeeAdapter(viewModel::itemSelectionChanged)
        }
    }

    private fun initObservers(binding: FragmentEmployeesSelectionBinding) {
        viewModel.employeesLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.employeesLiveData.observe(viewLifecycleOwner) {
            binding.employeesRecyclerView.adapterAction { setItems(it) }
        }
        viewModel.checkedEmployeesLiveData.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe)
                .previousBackStackEntry?.savedStateHandle?.set(SELECTED_EMPLOYEES_DATA_KEY, it)
        }
    }

    private fun RecyclerView.adapterAction(action: SelectableEmployeeAdapter.() -> Unit) {
        (adapter as? SelectableEmployeeAdapter)?.action()
    }
}