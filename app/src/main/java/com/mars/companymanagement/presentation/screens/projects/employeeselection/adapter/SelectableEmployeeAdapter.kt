package com.mars.companymanagement.presentation.screens.projects.employeeselection.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.projects.employeeselection.adapter.viewholder.SelectableEmployeeViewHolder
import com.mars.companymanagement.presentation.screens.projects.employeeselection.models.SelectableEmployeeViewData

class SelectableEmployeeAdapter(
    private val itemSelectionChanged:(id: String, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<SelectableEmployeeViewHolder>() {

    private val items: MutableList<SelectableEmployeeViewData> = mutableListOf()

    fun setItems(items: List<SelectableEmployeeViewData>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectableEmployeeViewHolder {
        return SelectableEmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SelectableEmployeeViewHolder, position: Int) {
        holder.bind(items[position], itemSelectionChanged)
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: SelectableEmployeeViewHolder) {
        holder.recycle()
    }
}