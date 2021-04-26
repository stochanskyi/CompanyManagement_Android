package com.mars.companymanagement.presentation.screens.employees.list.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.databinding.ViewHolderEmployeeBinding
import com.mars.companymanagement.presentation.screens.employees.list.models.EmployeeViewData

class EmployeeViewHolder private constructor(
    private val binding: ViewHolderEmployeeBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = EmployeeViewHolder(
            ViewHolderEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(model: EmployeeViewData, onClickBlock: (String) -> Unit) {
        binding.run {
            nameTextView.text = model.name
            positionTextView.text = model.position
            root.setOnClickListener { onClickBlock(model.id) }
        }
    }

    fun recycle() {
        binding.root.setOnClickListener(null)
    }

}