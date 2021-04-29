package com.mars.companymanagement.presentation.screens.projects.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.databinding.ViewHolderEmployeeSalaryBinding
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectEmployeeViewData

class ProjectEmployeeViewHolder private constructor(
    private val binding: ViewHolderEmployeeSalaryBinding
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = ProjectEmployeeViewHolder(
            ViewHolderEmployeeSalaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(
        model: ProjectEmployeeViewData,
        clickBlock: (String) -> Unit
    ) {
        binding.run {
            nameTextView.text = model.name
            positionTextView.text = model.position
            salaryTextView.text = model.salary

            root.setOnClickListener { clickBlock(model.id) }
        }
    }

    fun recycle() {
        binding.root.setOnClickListener(null)
    }

}