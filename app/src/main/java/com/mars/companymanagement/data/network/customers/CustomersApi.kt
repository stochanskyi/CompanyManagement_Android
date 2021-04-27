package com.mars.companymanagement.data.network.customers

import com.mars.companymanagement.data.network.customers.models.response.CustomerResponse
import retrofit2.Response
import retrofit2.http.GET

interface CustomersApi {
    @GET("customers")
    suspend fun getCustomers(): Response<List<CustomerResponse>>
}