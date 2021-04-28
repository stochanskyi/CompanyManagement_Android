package com.mars.companymanagement.presentation.screens.customers.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.customers.models.UpdateCustomerData
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class EditCustomerBehaviour(private val preliminaryData: Customer) : ChangeCustomerBehaviour {
    override fun getPreliminaryData(): Customer = preliminaryData

    override suspend fun applyChanges(repository: CustomersRepository, changes: CustomerChanges): RequestResult<Customer> {
        return repository.updateCustomer(changes.createUpdateEmployeeData())
    }

    private fun CustomerChanges.createUpdateEmployeeData() = UpdateCustomerData(
        preliminaryData.id,
        firstName ?: preliminaryData.firstName,
        lastName ?: preliminaryData.lastName,
        country ?: preliminaryData.country,
        email ?: preliminaryData.email,
        phoneNumber ?: preliminaryData.phoneNumber
    )
}