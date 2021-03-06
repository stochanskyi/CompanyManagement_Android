package com.mars.companymanagement.presentation.screens.transactions.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.FragmentTransactionsBinding
import com.mars.companymanagement.presentation.screens.transactions.create.type.TransactionTypeDialog
import com.mars.companymanagement.presentation.screens.transactions.create.type.TransactionTypeListener
import com.mars.companymanagement.presentation.screens.transactions.list.adapter.TransactionsAdapter
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.AllTransactionsBehaviour
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.IncomingTransactionsBehaviour
import com.mars.companymanagement.presentation.screens.transactions.list.behaviour.OutgoingTransactionsBehaviour
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions), TransactionTypeListener {
    private val viewModel: TransactionsViewModel by viewModels()
    private val args: TransactionsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val behaviour = when (args.behaviourTitle) {
            "All" -> AllTransactionsBehaviour()
            "Outgoing" -> OutgoingTransactionsBehaviour()
            "Incoming" -> IncomingTransactionsBehaviour()
            else -> throw IllegalStateException()
        }
        viewModel.setup(behaviour)
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
        binding.addTransactionButton.setOnClickListener {
            viewModel.addTransaction()
        }
    }

    private fun initObservers(binding: FragmentTransactionsBinding) {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.transactionsLiveData.observe(viewLifecycleOwner) {
            (binding.transactionsRecyclerView.adapter as? TransactionsAdapter)?.setItems(it)
        }
        viewModel.openCreateEmployeeTransaction.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigate(R.id.to_create_employee_transaction)
        }
        viewModel.openCreateCustomerTransaction.observe(viewLifecycleOwner) {
            Navigation.findNavController(view ?: return@observe).navigate(R.id.to_create_customer_transaction)
        }
        viewModel.openSelectTransactionTypeLiveData.observe(viewLifecycleOwner) {
            TransactionTypeDialog().show(childFragmentManager, "Type_Fragment")
        }
    }

    override fun onIncomingSelected() {
        Navigation.findNavController(view ?: return).navigate(R.id.to_create_customer_transaction)
    }

    override fun onOutgoingSelected() {
        Navigation.findNavController(view ?: return).navigate(R.id.to_create_employee_transaction)
    }
}