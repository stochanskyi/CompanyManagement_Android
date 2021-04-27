package com.mars.companymanagement.data.repositories.customers

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.customers.models.CustomersDataSource
import com.mars.companymanagement.data.network.customers.models.response.CustomerResponse
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

interface CustomersRepository {
    suspend fun getCustomers(): RequestResult<List<Customer>>
}

class CustomersRepositoryImpl @Inject constructor(
    private val customersDataSource: CustomersDataSource
) : CustomersRepository {

    override suspend fun getCustomers(): RequestResult<List<Customer>> = withIoContext {
        customersDataSource.getCustomers().map { it.parse() }
    }

    private fun List<CustomerResponse>.parse(): List<Customer> = map { it.parse() }

    private fun CustomerResponse.parse(): Customer {
        return Customer(id.toString(), firstName, lastName, country, email, phoneNumber)
    }

}
