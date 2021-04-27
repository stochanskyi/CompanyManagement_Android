package com.mars.companymanagement.presentation.screens.projects.list.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.databinding.ViewHolderProjectBinding
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData

class ProjectViewHolder private constructor(
    private val binding: ViewHolderProjectBinding
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = ProjectViewHolder(
            ViewHolderProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun bind(model: ProjectViewData, onClickBlock: (String) -> Unit) {
        binding.run {
            nameTextView.text = model.name
            statusTextView.text = model.status

            root.setOnClickListener { onClickBlock(model.id) }
        }
    }

    fun recycle() {
        binding.root.setOnClickListener(null)
    }
}