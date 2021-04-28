package com.mars.companymanagement.presentation.screens.customers.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.CreateCustomerData
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class CreateCustomerBehaviour : ChangeCustomerBehaviour {
    override fun getPreliminaryData(): Customer? = null

    override suspend fun applyChanges(repository: CustomersRepository, changes: CustomerChanges): RequestResult<Customer> {
        return repository.createCustomer(changes.createAddCustomerData())
    }

    private fun CustomerChanges.createAddCustomerData(): CreateCustomerData {
        return CreateCustomerData(
            firstName!!,
            lastName!!,
            country!!,
            email!!,
            phoneNumber!!
        )
    }
}