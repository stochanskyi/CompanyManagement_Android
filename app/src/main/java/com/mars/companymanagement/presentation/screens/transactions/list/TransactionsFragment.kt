package com.mars.companymanagement.presentation.screens.transactions.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentTransactionsBinding
import com.mars.companymanagement.presentation.screens.transactions.list.adapter.TransactionsAdapter
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.AllTransactionsBehaviour
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {
    private val viewModel: TransactionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(AllTransactionsBehaviour())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentTransactionsBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: FragmentTransactionsBinding) {
        binding.transactionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TransactionsAdapter(viewModel::openTransactionDetails)
        }
    }

    private fun initObservers(binding: FragmentTransactionsBinding) {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.transactionsLiveData.observe(viewLifecycleOwner) {
            (binding.transactionsRecyclerView.adapter as? TransactionsAdapter)?.setItems(it)
        }
    }
}