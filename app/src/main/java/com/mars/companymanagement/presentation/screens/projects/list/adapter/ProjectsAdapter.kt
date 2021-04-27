package com.mars.companymanagement.presentation.screens.projects.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.projects.list.adapter.viewholders.ProjectViewHolder
import com.mars.companymanagement.presentation.screens.projects.list.models.ProjectViewData

class ProjectsAdapter(
    private val itemClickBlock: (id: String) -> Unit
) : RecyclerView.Adapter<ProjectViewHolder>() {

    private val items: MutableList<ProjectViewData> = mutableListOf()

    fun setItems(items: List<ProjectViewData>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProjectViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(items[position], itemClickBlock)
    }

    override fun getItemCount() = items.size

    override fun onViewRecycled(holder: ProjectViewHolder) {
        holder.recycle()
    }
}