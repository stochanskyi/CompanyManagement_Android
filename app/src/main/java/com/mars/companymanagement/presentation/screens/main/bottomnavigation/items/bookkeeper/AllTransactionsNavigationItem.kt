package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.bookkeeper

import com.mars.companymanagement.R
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem

class AllTransactionsNavigationItem : NavigationItem {
    companion object {
        const val ITEM_TAG = "all_transactions"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_all_transactions

    override val navigationGraphId: Int = R.navigation.all_transactions_nav_graph
}