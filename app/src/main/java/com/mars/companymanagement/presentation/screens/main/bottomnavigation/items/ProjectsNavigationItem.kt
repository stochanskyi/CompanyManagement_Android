package com.mars.companymanagement.presentation.screens.main.bottomnavigation.items

import com.mars.companymanagement.R

class ProjectsNavigationItem : NavigationItem {

    companion object {
        const val ITEM_TAG = "projects_item_tag"
    }

    override val tag: String = ITEM_TAG

    override val menuId: Int = R.id.action_projects

    override val navigationGraphId: Int = R.navigation.projects_nav_graph
}