package com.mars.companymanagement.presentation.screens.transactions.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.companymanagement.presentation.screens.transactions.list.adapter.viewholders.TransactionVieHolder
import com.mars.companymanagement.presentation.screens.transactions.list.model.TransactionViewData

class TransactionsAdapter(
    private val itemClickBlock: (String) -> Unit
) : RecyclerView.Adapter<TransactionVieHolder>() {

    private var items: List<TransactionViewData> = emptyList()

    fun setItems(items: List<TransactionViewData>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVieHolder {
        return TransactionVieHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionVieHolder, position: Int) {
        holder.bind(items[position], itemClickBlock)
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: TransactionVieHolder) {
        holder.recycle()
    }
}