package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.admin

import com.mars.companymanagement.R
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem

class EmployeesNavigationItem : NavigationItem {

    companion object {
        const val ITEM_TAG = "employees_item_tag"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_employees

    override val navigationGraphId: Int = R.navigation.employees_nav_graph
}