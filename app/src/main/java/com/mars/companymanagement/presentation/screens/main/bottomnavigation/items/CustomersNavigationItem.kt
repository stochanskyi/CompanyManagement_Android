package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items

import com.mars.companymanagement.R

class CustomersNavigationItem : NavigationItem {
    companion object {
        const val ITEM_TAG = "customers_item_tag"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_customers

    override val navigationGraphId: Int = R.navigation.customers_nav_graph
}