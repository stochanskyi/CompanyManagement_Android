package com.mars.companymanagement.presentation.screens.customers.list.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.databinding.ViewHolderCustomerBinding
import com.mars.companymanagement.presentation.screens.customers.list.models.CustomerViewData

class CustomerViewHolder private constructor(
    private val binding: ViewHolderCustomerBinding
): RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun create(parent: ViewGroup) = CustomerViewHolder(
            ViewHolderCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(model: CustomerViewData, clickBlock: (String) -> Unit) {
        binding.run {
            nameTextView.text = model.name
            countryTextView.text = model.country

            root.setOnClickListener { clickBlock(model.id) }
        }
    }

    fun recycle() {
        binding.root.setOnClickListener(null)
    }

}