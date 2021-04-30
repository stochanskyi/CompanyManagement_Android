package com.mars.companymanagement.data.network.transactions

import com.mars.companymanagement.data.network.transactions.request.CustomerTransacctionRequest
import com.mars.companymanagement.data.network.transactions.request.EmployeeTransactionRequest
import com.mars.companymanagement.data.network.transactions.response.CustomerDetailResponse
import com.mars.companymanagement.data.network.transactions.response.EmployeeDetailsResponse
import com.mars.companymanagement.data.network.transactions.response.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionsApi {

    @GET("transactions/all")
    suspend fun getAllTransactions(): Response<List<TransactionResponse>>

    @POST("transactions/incoming/add")
    suspend fun addCustomerTransaction(@Body request: CustomerTransacctionRequest): Response<TransactionResponse>

    @POST("transactions/outgoing/add")
    suspend fun addEmployeeTransaction(@Body request: EmployeeTransactionRequest): Response<TransactionResponse>

    @GET("transaction/outgoing/details")
    suspend fun getEmployeeTransactionDetails(@Query("id") id: Int): Response<EmployeeDetailsResponse>

    @GET("transaction/incoming/details")
    suspend fun getCustomerTransactionDetails(@Query("id") id: Int): Response<CustomerDetailResponse>
}