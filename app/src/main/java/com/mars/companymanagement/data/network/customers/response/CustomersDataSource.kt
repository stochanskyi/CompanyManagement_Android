package com.mars.companymanagement.data.network.customers.response

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.customers.CustomersApi
import com.mars.companymanagement.data.network.customers.request.CreateCustomerRequest
import com.mars.companymanagement.data.network.customers.request.UpdateCustomerRequest
import com.mars.companymanagement.data.network.customers.response.response.CustomerResponse
import javax.inject.Inject

interface CustomersDataSource {
    suspend fun getCustomers(): RequestResult<List<CustomerResponse>>
    suspend fun updateCustomer(request: UpdateCustomerRequest): RequestResult<CustomerResponse>
    suspend fun createCustomer(request: CreateCustomerRequest): RequestResult<CustomerResponse>
}

class CustomersDataSourceImpl @Inject constructor(
    private val customersApi: CustomersApi,
    private val gson: Gson
) : CustomersDataSource {

    override suspend fun getCustomers(): RequestResult<List<CustomerResponse>> {
        return customersApi.getCustomers().asRequestResult(gson)
    }

    override suspend fun updateCustomer(request: UpdateCustomerRequest): RequestResult<CustomerResponse> {
        return customersApi.updateCustomer(request).asRequestResult(gson)
    }

    override suspend fun createCustomer(request: CreateCustomerRequest): RequestResult<CustomerResponse> {
        return customersApi.createCustomer(request).asRequestResult(gson)
    }

}