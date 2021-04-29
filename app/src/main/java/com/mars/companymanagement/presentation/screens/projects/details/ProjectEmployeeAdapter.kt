package com.mars.companymanagement.presentation.screens.projects.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.projects.details.adapter.ProjectEmployeeViewHolder
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectEmployeeViewData

class ProjectEmployeeAdapter(
    private val itemClickBlock: (String) -> Unit
) : RecyclerView.Adapter<ProjectEmployeeViewHolder>() {

    private val items: MutableList<ProjectEmployeeViewData> = mutableListOf()

    fun setItems(items: List<ProjectEmployeeViewData>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectEmployeeViewHolder {
        return ProjectEmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProjectEmployeeViewHolder, position: Int) {
        holder.bind(items[position], itemClickBlock)
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: ProjectEmployeeViewHolder) {
        holder.recycle()
    }
}