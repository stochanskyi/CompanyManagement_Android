package com.mars.companymanagement.presentation.screens.projects.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.projects.details.adapter.ProjectEmployeeViewHolder
import com.mars.companymanagement.presentation.screens.projects.details.models.ProjectEmployeeViewData

private const val CHANGE_SALARY_PAYLOAD = "CHANGE_SALARY_PAYLOAD"

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

    fun setSalary(id: String, salary: String) {
        val itemIndex = items.indexOfFirst { it.id == id }.takeIf { it >= 0} ?: return

        items[itemIndex].salary = salary
        notifyItemChanged(itemIndex, CHANGE_SALARY_PAYLOAD)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectEmployeeViewHolder {
        return ProjectEmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProjectEmployeeViewHolder, position: Int) {
        holder.bind(items[position], itemClickBlock)
    }

    override fun onBindViewHolder(
        holder: ProjectEmployeeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        payloads.forEach {
            if (it == CHANGE_SALARY_PAYLOAD) {
                holder.setSalary(items[position].salary)
            } else {
                super.onBindViewHolder(holder, position, payloads)

            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: ProjectEmployeeViewHolder) {
        holder.recycle()
    }
}