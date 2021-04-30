package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.bookkeeper

import com.mars.companymanagement.R
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem

class OutgoingTransactionsNavigationItem : NavigationItem {
    companion object {
        const val ITEM_TAG = "outgoing_transactions"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_outgoing_transactions

    override val navigationGraphId: Int = R.navigation.outgoing_transactions_nav_graph
}