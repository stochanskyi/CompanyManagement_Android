package com.mars.companymanagement.data.repositories.user.models.levels

import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevel
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevelConstants
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.admin.CustomersNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.admin.EmployeesNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.admin.ProjectsNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.bookkeeper.AllTransactionsNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.bookkeeper.IncomingTransactionsNavigationItem
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.bookkeeper.OutgoingTransactionsNavigationItem

class BookkeeperAccessLevel : AccessLevel {
    override val levelId: String = AccessLevelConstants.BOOKKEEPER_LEVEL_CODE

    override fun provideItemsList(): List<NavigationItem> = listOf(
        AllTransactionsNavigationItem(),
        IncomingTransactionsNavigationItem(),
        OutgoingTransactionsNavigationItem()
    )

    override fun provideMenuRes(): Int = R.menu.menu_bookkeeper_bottom
}