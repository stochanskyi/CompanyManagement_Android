package com.mars.companymanagement.presentation.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition

class IdentifiableArrayAdapter(
    context: Context,
    resource: Int,
    objects: List<IdentifiableAdapterItem>
) : ArrayAdapter<String>(context, resource, objects.map { it.name }) {

    private val items: List<IdentifiableAdapterItem> = objects

    fun getItemPosition(id: String): Int? {
        return items.indexOfLast { it.id == id }.takeIf { it >= 0 }
    }

    fun getItemByPosition(position: Int) = items[position]
}