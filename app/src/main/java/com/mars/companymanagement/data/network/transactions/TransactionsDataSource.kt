package com.mars.companymanagement.data.network.transactions

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.transactions.request.CustomerTransacctionRequest
import com.mars.companymanagement.data.network.transactions.request.EmployeeTransactionRequest
import com.mars.companymanagement.data.network.transactions.response.CustomerDetailResponse
import com.mars.companymanagement.data.network.transactions.response.EmployeeDetailsResponse
import com.mars.companymanagement.data.network.transactions.response.TransactionResponse
import javax.inject.Inject

interface TransactionsDataSource {
    suspend fun getAllTransactions(): RequestResult<List<TransactionResponse>>
    suspend fun addCustomerTransaction(request: CustomerTransacctionRequest): RequestResult<TransactionResponse>
    suspend fun addEmployeeTransaction(request: EmployeeTransactionRequest): RequestResult<TransactionResponse>
    suspend fun getEmployeeTransactionDetails(id: Int): RequestResult<EmployeeDetailsResponse>
    suspend fun getCustomerTransactionDetails(id: Int): RequestResult<CustomerDetailResponse>
}

class TransactionsDataSourceImpl @Inject constructor(
    private val transactionsApi: TransactionsApi,
    private val gson: Gson
) : TransactionsDataSource {
    override suspend fun getAllTransactions(): RequestResult<List<TransactionResponse>> {
        return transactionsApi.getAllTransactions().asRequestResult(gson)
    }

    override suspend fun addCustomerTransaction(request: CustomerTransacctionRequest): RequestResult<TransactionResponse> {
        return transactionsApi.addCustomerTransaction(request).asRequestResult(gson)
    }

    override suspend fun addEmployeeTransaction(request: EmployeeTransactionRequest): RequestResult<TransactionResponse> {
        return transactionsApi.addEmployeeTransaction(request).asRequestResult(gson)
    }

    override suspend fun getEmployeeTransactionDetails(id: Int): RequestResult<EmployeeDetailsResponse> {
        return transactionsApi.getEmployeeTransactionDetails(id).asRequestResult(gson)
    }

    override suspend fun getCustomerTransactionDetails(id: Int): RequestResult<CustomerDetailResponse> {
        return transactionsApi.getCustomerTransactionDetails(id).asRequestResult(gson)
    }
}