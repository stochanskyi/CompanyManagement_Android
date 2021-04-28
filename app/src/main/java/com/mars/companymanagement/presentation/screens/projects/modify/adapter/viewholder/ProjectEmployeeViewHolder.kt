package com.mars.companymanagement.presentation.screens.projects.modify.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.databinding.ViewHolderProjectEmployeeBinding
import com.mars.companymanagement.presentation.screens.employees.list.models.EmployeeViewData

class ProjectEmployeeViewHolder private constructor(
    private val binding: ViewHolderProjectEmployeeBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = ProjectEmployeeViewHolder(
            ViewHolderProjectEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(model: EmployeeViewData) {
        binding.nameTextView.text = model.name
        binding.positionTextView.text = model.position
    }

}