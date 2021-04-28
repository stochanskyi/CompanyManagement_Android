package com.mars.companymanagement.presentation.screens.customers.modify.behaviour

import android.os.Parcelable
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerChanges

interface ChangeCustomerBehaviour : Parcelable {

    fun getPreliminaryData(): Customer?

    suspend fun applyChanges(repository: CustomersRepository, changes: CustomerChanges): RequestResult<Customer>
}