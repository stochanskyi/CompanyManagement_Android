package com.mars.companymanagement.presentation.screens.transactions.list.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.ViewHolderTransactionBinding
import com.mars.companymanagement.presentation.screens.transactions.list.model.TransactionViewData

class TransactionVieHolder private constructor(
    private val binding: ViewHolderTransactionBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup) = TransactionVieHolder(
            ViewHolderTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(model: TransactionViewData, clickBlock: (String) -> Unit) {
        binding.run {
            nameTextView.text = model.title
            dateTextView.text = model.date
            salaryTextView.text = model.amount

            val colorId = if (model.isIncoming) R.color.colorGreen else R.color.colorRed

            salaryTextView.setTextColor(itemView.context.getColor(colorId))

            root.setOnClickListener { clickBlock(model.id) }
        }
    }

    fun recycle() {
        binding.root.setOnClickListener(null)
    }
}