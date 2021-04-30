package com.mars.companymanagement.presentation.screens.transactions.create.type

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.DialogTransactionTypeBinding

class TransactionTypeDialog : DialogFragment() {

    private val parentListener: TransactionTypeListener? get() =
        (parentFragment as? TransactionTypeListener) ?: (activity as? TransactionTypeListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_transaction_type, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DialogTransactionTypeBinding.bind(view).run {
            initListeners(this)
        }
    }

    private fun initListeners(binding: DialogTransactionTypeBinding) {
        binding.incomingLayout.setOnClickListener {
            parentListener?.onIncomingSelected()
            dismiss()
        }
        binding.outgoingLayout.setOnClickListener {
            parentListener?.onOutgoingSelected()
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.decorView?.background?.alpha = 0
        }
    }

    override fun onResume() {
        super.onResume()

        val currentHeight = dialog?.window?.attributes?.height

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            currentHeight ?: WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

}