package com.mars.companymanagement.presentation.screens.main.toolbar

import androidx.lifecycle.LifecycleOwner

interface ToolbarConfigurator {
    fun modifyToolbarConfiguration(modifyAction: ToolbarConfigurationChanges.() -> Unit)

    fun     setMenuItemsListener(lifecycleOwner: LifecycleOwner, listener: ToolbarMenuListener)
}