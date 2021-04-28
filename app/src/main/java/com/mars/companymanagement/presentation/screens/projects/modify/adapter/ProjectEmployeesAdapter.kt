package com.mars.companymanagement.presentation.screens.projects.modify.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.employees.list.models.EmployeeViewData
import com.mars.companymanagement.presentation.screens.projects.modify.adapter.viewholder.ProjectEmployeeViewHolder

class ProjectEmployeesAdapter : RecyclerView.Adapter<ProjectEmployeeViewHolder>() {

    private var items: MutableList<EmployeeViewData> = mutableListOf()

    fun setItems(items: List<EmployeeViewData>) {
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}