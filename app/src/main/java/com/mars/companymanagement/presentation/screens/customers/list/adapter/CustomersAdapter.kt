package com.mars.companymanagement.presentation.screens.customers.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.customers.list.adapter.viewholders.CustomerViewHolder
import com.mars.companymanagement.presentation.screens.customers.list.models.ItemViewData

class CustomersAdapter(
    private val itemClickBlock: (id: String) -> Unit
): RecyclerView.Adapter<CustomerViewHolder>() {

    private val items: MutableList<ItemViewData> = mutableListOf()

    fun setItems(items: List<ItemViewData>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CustomerViewHolder.create(parent)

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(items[position], itemClickBlock)
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: CustomerViewHolder) {
        holder.recycle()
    }
}