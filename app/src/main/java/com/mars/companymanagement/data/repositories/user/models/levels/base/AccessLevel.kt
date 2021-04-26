package com.mars.companymanagement.data.repositories.user.models.levels.base

import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem

interface AccessLevel {
    val levelId: String

    fun provideItemsList(): List<NavigationItem>

    fun provideMenuRes(): Int
}