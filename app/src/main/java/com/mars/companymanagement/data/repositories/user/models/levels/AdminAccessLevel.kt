package com.mars.companymanagement.data.repositories.user.models.levels

import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevel
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevelConstants
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.CustomersNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.EmployeesNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.ProjectsNavigationItem

class AdminAccessLevel : AccessLevel {
    override val levelId: String = AccessLevelConstants.ADMIN_LEVEL_CODE

    override fun provideItemsList(): List<NavigationItem> = listOf(
        EmployeesNavigationItem(),
        CustomersNavigationItem(),
        ProjectsNavigationItem()
    )

    override fun provideMenuRes(): Int = R.menu.menu_admin_bottom
}