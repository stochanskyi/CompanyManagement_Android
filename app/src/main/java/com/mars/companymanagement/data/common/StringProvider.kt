package com.mars.companymanagement.data.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringProvider {
    fun getString(@StringRes resInt: Int, vararg args: Any): String
}

class StringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): StringProvider {
    override fun getString(resInt: Int, vararg args: Any): String = context.getString(resInt, *args)
}