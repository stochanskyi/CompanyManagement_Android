package com.mars.companymanagement.data.repositories.customers

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.customers.request.CreateCustomerRequest
import com.mars.companymanagement.data.network.customers.request.UpdateCustomerRequest
import com.mars.companymanagement.data.network.customers.response.CustomersDataSource
import com.mars.companymanagement.data.network.customers.response.response.CustomerResponse
import com.mars.companymanagement.data.repositories.customers.models.CreateCustomerData
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.customers.models.UpdateCustomerData
import com.mars.companymanagement.ext.withIoContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface CustomersRepository {
    val customerUpdatedFlow: Flow<Customer>
    val customerCreatedFlow: Flow<Customer>

    suspend fun getCustomers(): RequestResult<List<Customer>>
    suspend fun updateCustomer(updateCustomerData: UpdateCustomerData): RequestResult<Customer>
    suspend fun createCustomer(createCustomerData: CreateCustomerData): RequestResult<Customer>
}

class CustomersRepositoryImpl @Inject constructor(
    private val customersDataSource: CustomersDataSource
) : CustomersRepository {
    override val customerUpdatedFlow: MutableSharedFlow<Customer> = MutableSharedFlow()

    override val customerCreatedFlow: MutableSharedFlow<Customer> = MutableSharedFlow()

    override suspend fun getCustomers(): RequestResult<List<Customer>> = withIoContext {
        customersDataSource.getCustomers().map { it.parse() }
    }

    override suspend fun updateCustomer(updateCustomerData: UpdateCustomerData): RequestResult<Customer> {
        return withIoContext {
            customersDataSource.updateCustomer(updateCustomerData.createRequest())
                .map { it.parse() }
                .suspendOnSuccess { customerUpdatedFlow.emit(it) }
        }
    }

    override suspend fun createCustomer(createCustomerData: CreateCustomerData): RequestResult<Customer> {
        return withIoContext {
            customersDataSource.createCustomer(createCustomerData.createRequest())
                .map { it.parse() }
                .suspendOnSuccess { customerCreatedFlow.emit(it) }
        }
    }

    private fun List<CustomerResponse>.parse(): List<Customer> = map { it.parse() }

    private fun CustomerResponse.parse(): Customer {
        return Customer(id.toString(), firstName, lastName, country, email, phoneNumber)
    }

    private fun UpdateCustomerData.createRequest() = UpdateCustomerRequest(
        id.toInt(), firstName, lastName, country, email, phoneNumber
    )
    private fun CreateCustomerData.createRequest() = CreateCustomerRequest(
        firstName, lastName, country, email, phoneNumber
    )

}
