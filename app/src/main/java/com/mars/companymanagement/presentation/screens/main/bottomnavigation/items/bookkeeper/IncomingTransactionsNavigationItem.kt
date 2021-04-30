package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.bookkeeper

import com.mars.companymanagement.R
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem

class IncomingTransactionsNavigationItem : NavigationItem {
    companion object {
        const val ITEM_TAG = "incoming_transactions"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_incoming_transactions

    override val navigationGraphId: Int = R.navigation.incoming_transactions_nav_graph
}