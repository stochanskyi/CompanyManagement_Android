package com.mars.companymanagement.presentation.screens.customers.details

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
import com.mars.companymanagement.databinding.FragmentCustomerDetailsBinding
import com.mars.companymanagement.presentation.screens.customers.details.models.CustomerInfoViewData
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.projects.list.adapter.ProjectsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerDetailsFragment : Fragment(R.layout.fragment_customer_details) {
    private val viewModel: CustomerDetailsViewModel by viewModels()
    private val args: CustomerDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(args.customerInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCustomerDetailsBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentCustomerDetailsBinding) {
        binding.projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ProjectsAdapter(viewModel::openProjectDetails)
        }
    }

    private fun initObservers(binding: FragmentCustomerDetailsBinding) {
        viewModel.projectsLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.projectsRecyclerView.isVisible = !it
        }
        viewModel.customerInfoLiveData.observe(viewLifecycleOwner) {
            setCustomerInfo(binding, it)
        }
        viewModel.projectsLiveData.observe(viewLifecycleOwner) {
            binding.projectsRecyclerView.adapterAction { setItems(it) }
        }
        viewModel.openProjectDetailsLiveData.observe(viewLifecycleOwner) {
            val action = CustomerDetailsFragmentDirections.toProjectDetails(it)
            Navigation.findNavController(view ?: return@observe).navigate(action)
        }
    }

    private fun setCustomerInfo(binding: FragmentCustomerDetailsBinding, info: CustomerInfoViewData) {
        binding.run {
            (activity as? ToolbarConfigurator)?.modifyToolbarConfiguration { name = info.name }
            nameTextView.text = info.name
            countryTextView.text = info.country
            emailTextView.text = info.email
            phoneNumberTextView.text = info.phoneNumber
        }
    }

    private fun RecyclerView.adapterAction(action: ProjectsAdapter.() -> Unit) {
        (adapter as? ProjectsAdapter)?.action()
    }
}