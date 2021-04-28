package com.mars.companymanagement.presentation.screens.projects.employeeselection.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.databinding.ViewHolderSelectableEmployeeBinding
import com.mars.companymanagement.presentation.screens.projects.employeeselection.models.SelectableEmployeeViewData

class SelectableEmployeeViewHolder private constructor(
    private val binding: ViewHolderSelectableEmployeeBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = SelectableEmployeeViewHolder(
            ViewHolderSelectableEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(model: SelectableEmployeeViewData, changedBlock: (String, Boolean) -> Unit) {
        binding.run {
            nameTextView.text = model.name
            positionTextView.text = model.position

            checkbox.isChecked = model.isChecked

            root.setOnClickListener {
                checkbox.isChecked = checkbox.isChecked.not()
                model.isChecked = checkbox.isChecked
                changedBlock(model.id, model.isChecked)
            }
        }
    }

    fun recycle() {
        binding.root.setOnClickListener(null)
    }
}