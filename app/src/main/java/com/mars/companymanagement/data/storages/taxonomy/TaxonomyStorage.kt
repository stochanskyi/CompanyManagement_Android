package com.mars.companymanagement.data.storages.taxonomy

import com.mars.companymanagement.data.storages.taxonomy.model.TaxonomyItem
import javax.inject.Inject

interface TaxonomyStorage {
    fun getPositions(): List<TaxonomyItem>
    fun getProjectStatuses(): List<TaxonomyItem>

    fun setPosition(positions: List<TaxonomyItem>)
    fun setProjectStatuses(statuses: List<TaxonomyItem>)
}

class TaxonomyStorageImpl @Inject constructor() : TaxonomyStorage {

    private val positions: MutableList<TaxonomyItem> = mutableListOf()
    private val statuses: MutableList<TaxonomyItem> = mutableListOf()

    override fun getPositions(): List<TaxonomyItem> {
        return positions.toList()
    }

    override fun getProjectStatuses(): List<TaxonomyItem> {
        return statuses.toList()
    }

    override fun setPosition(positions: List<TaxonomyItem>) {
        this.positions.apply {
            clear()
            addAll(positions)
        }
    }

    override fun setProjectStatuses(statuses: List<TaxonomyItem>) {
        this.statuses.apply {
            clear()
            addAll(statuses)
        }
    }
}