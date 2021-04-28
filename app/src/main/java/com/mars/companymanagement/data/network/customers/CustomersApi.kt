package com.mars.companymanagement.data.network.customers

import com.mars.companymanagement.data.network.customers.request.CreateCustomerRequest
import com.mars.companymanagement.data.network.customers.request.UpdateCustomerRequest
import com.mars.companymanagement.data.network.customers.response.response.CustomerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CustomersApi {
    @GET("customers")
    suspend fun getCustomers(): Response<List<CustomerResponse>>

    @POST("customers/update")
    suspend fun updateCustomer(@Body request: UpdateCustomerRequest): Response<CustomerResponse>

    @POST("customers/add")
    suspend fun createCustomer(@Body request: CreateCustomerRequest): Response<CustomerResponse>
}