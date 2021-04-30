package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.admin

import com.mars.companymanagement.R
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem

class ProjectsNavigationItem : NavigationItem {

    companion object {
        const val ITEM_TAG = "projects_item_tag"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_projects

    override val navigationGraphId: Int = R.navigation.projects_nav_graph
}