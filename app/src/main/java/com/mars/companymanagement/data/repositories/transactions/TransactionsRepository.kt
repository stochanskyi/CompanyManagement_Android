package com.mars.companymanagement.data.repositories.transactions

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.transactions.TransactionsDataSource
import com.mars.companymanagement.data.network.transactions.request.CustomerTransacctionRequest
import com.mars.companymanagement.data.network.transactions.request.EmployeeTransactionRequest
import com.mars.companymanagement.data.network.transactions.response.ConfirmerResponse
import com.mars.companymanagement.data.network.transactions.response.CustomerDetailResponse
import com.mars.companymanagement.data.network.transactions.response.EmployeeDetailsResponse
import com.mars.companymanagement.data.network.transactions.response.TransactionResponse
import com.mars.companymanagement.data.repositories.transactions.model.*
import com.mars.companymanagement.ext.withIoContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random

interface TransactionsRepository {

    val incomingTransactionFlow: Flow<Transaction>
    val outgoingTransactionFlow: Flow<Transaction>

    suspend fun getTransactions(): RequestResult<List<Transaction>>

    suspend fun createEmployeeTransaction(
        employeeId: String,
        amount: Float
    ): RequestResult<Transaction>

    suspend fun createCustomerTransaction(
        customerId: String,
        projectId: String,
        amount: Float
    ): RequestResult<Transaction>

    suspend fun getEmployeeTransactionDetails(id: String): RequestResult<EmployeeTransactionDetails>

    suspend fun getCustomerTransactionDetails(id: String): RequestResult<CustomerTransactionDetails>

}

private const val DATE_SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
private const val SERVER_ZONE_ID = "UTC"

class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsDataSource: TransactionsDataSource
) : TransactionsRepository {

    override val incomingTransactionFlow: MutableSharedFlow<Transaction> = MutableSharedFlow()

    override val outgoingTransactionFlow: MutableSharedFlow<Transaction> = MutableSharedFlow()

    override suspend fun getTransactions(): RequestResult<List<Transaction>> = withIoContext {
        delay(Random.nextLong(1000, 4000))
        transactionsDataSource.getAllTransactions().map { it.parse() }
    }

    override suspend fun createEmployeeTransaction(
        employeeId: String,
        amount: Float
    ): RequestResult<Transaction> {
        return withIoContext {
            transactionsDataSource.addEmployeeTransaction(
                EmployeeTransactionRequest(employeeId.toInt(), amount)
            )
                .map { it.parse() }
                .suspendOnSuccess { outgoingTransactionFlow.emit(it) }
        }
    }

    override suspend fun createCustomerTransaction(
        customerId: String,
        projectId: String,
        amount: Float
    ): RequestResult<Transaction> {
        return withIoContext {
            transactionsDataSource.addCustomerTransaction(
                CustomerTransacctionRequest(customerId.toInt(), projectId.toInt(), amount)
            )
                .map { it.parse() }
                .suspendOnSuccess { incomingTransactionFlow.emit(it) }
        }
    }

    override suspend fun getEmployeeTransactionDetails(id: String): RequestResult<EmployeeTransactionDetails> {
        return withIoContext {
            transactionsDataSource.getEmployeeTransactionDetails(id.toInt()).map { it.parse() }
        }
    }

    override suspend fun getCustomerTransactionDetails(id: String): RequestResult<CustomerTransactionDetails> {
        return withIoContext {
            transactionsDataSource.getCustomerTransactionDetails(id.toInt()).map { it.parse() }
        }
    }

    private fun List<TransactionResponse>.parse() = map { it.parse() }

    private fun TransactionResponse.parse() = Transaction(
        id.toString(),
        person,
        amount,
        parseDate(date),
        isIncome
    )

    private fun parseDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(DATE_SERVER_PATTERN).withZone(
            ZoneId.of(SERVER_ZONE_ID)
        )
        return ZonedDateTime.parse(date, formatter)
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDate()
    }

    private fun EmployeeDetailsResponse.parse() = EmployeeTransactionDetails(
        paymentId.toString(),
        amount,
        parseDate(paymentDate),
        receiver.parse(),
        confirmer.parse()
    )

    private fun CustomerDetailResponse.parse() = CustomerTransactionDetails(
        id.toString(),
        amount,
        parseDate(paymentDate),
        customer.parse(),
        confirmer.parse()
    )

    private fun EmployeeDetailsResponse.Receiver.parse() = TransactionEmployee(
        id.toString(),
        "$firstName $lastName",
        position
    )

    private fun CustomerDetailResponse.Customer.parse() = TransactionCustomer(
        id.toString(),
        "$firstName $lastName",
        country
    )

    private fun ConfirmerResponse.parse() = Confirmer(id.toString(), name)
}