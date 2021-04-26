package com.mars.companymanagement.presentation.screens.employees.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.employees.list.adapter.viewholder.EmployeeViewHolder
import com.mars.companymanagement.presentation.screens.employees.list.models.EmployeeViewData

class EmployeesAdapter(
    private val itemClickBlock: (id: String) -> Unit
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    private val items: MutableList<EmployeeViewData> = mutableListOf()

    fun setItems(items: List<EmployeeViewData>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(items[position], itemClickBlock)
    }

    override fun getItemCount() = items.size

    override fun onViewRecycled(holder: EmployeeViewHolder) {
        holder.recycle()
    }
}