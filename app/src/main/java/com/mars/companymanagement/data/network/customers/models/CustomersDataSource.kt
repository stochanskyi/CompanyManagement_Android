package com.mars.companymanagement.data.network.customers.models

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.customers.CustomersApi
import com.mars.companymanagement.data.network.customers.models.response.CustomerResponse
import javax.inject.Inject

interface CustomersDataSource {
    suspend fun getCustomers(): RequestResult<List<CustomerResponse>>
}

class CustomersDataSourceImpl @Inject constructor(
    private val customersApi: CustomersApi,
    private val gson: Gson
) : CustomersDataSource {

    override suspend fun getCustomers(): RequestResult<List<CustomerResponse>> {
        return customersApi.getCustomers().asRequestResult(gson)
    }

}