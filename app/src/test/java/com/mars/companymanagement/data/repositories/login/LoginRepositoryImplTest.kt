package com.mars.companymanagement.data.repositories.login

import com.mars.companymanagement.app.dispatchers.AppDispatchers
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.auth.LoginDataSource
import com.mars.companymanagement.data.network.auth.models.response.LoginResponse
import com.mars.companymanagement.data.network.taxonomy.TaxonomyDataSource
import com.mars.companymanagement.data.network.taxonomy.models.TaxonomyResponse
import com.mars.companymanagement.data.storages.taxonomy.TaxonomyStorage
import com.mars.companymanagement.data.storages.taxonomy.model.TaxonomyItem
import com.mars.companymanagement.data.storages.user.UserDataModel
import com.mars.companymanagement.data.storages.user.UserStorage
import com.mars.rules.MainDispatcherRule
import com.mars.rules.MockkAnnotationsRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginRepositoryImplTest {

    companion object {
        private const val TEST_EMAIL = "test@mail.com"
        private const val TEST_PASSWORD = "Aa123456"
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(AppDispatchers.Main)

    @get:Rule
    val mockkAnnotationRule = MockkAnnotationsRule(this)

    @MockK
    lateinit var loginDataSource: LoginDataSource

    @MockK(relaxUnitFun = true)
    lateinit var taxonomyDataSource: TaxonomyDataSource

    @MockK(relaxUnitFun = true)
    lateinit var userStorage: UserStorage

    @MockK(relaxUnitFun = true)
    lateinit var taxonomyStorage: TaxonomyStorage

    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        loginRepository = LoginRepositoryImpl(
            loginDataSource,
            taxonomyDataSource,
            userStorage,
            taxonomyStorage
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When login, if login success, then return success`() = runBlockingTest {
        val response = loginResponse()
        coEvery { loginDataSource.login(any()) } returns RequestResult.Success(response)

        coEvery { taxonomyDataSource.getTaxonomies() } returns
                RequestResult.Success(emptyTaxonomyResponse())

        val result = loginRepository.login(TEST_EMAIL, TEST_PASSWORD)

        assertTrue(result is RequestResult.Success)
    }

    @Test
    fun `When login, if login success, then save user`() = runBlockingTest {
        val response = loginResponse()
        coEvery { loginDataSource.login(any()) } returns RequestResult.Success(response)

        coEvery { taxonomyDataSource.getTaxonomies() } returns
                RequestResult.Success(emptyTaxonomyResponse())

        loginRepository.login(TEST_EMAIL, TEST_PASSWORD)

        coVerify { userStorage.setUser(userDataModel()) }
    }

    @Test
    fun `When login, if login success, then load and save taxonomies`() = runBlockingTest {
        val response = loginResponse()
        coEvery { loginDataSource.login(any()) } returns RequestResult.Success(response)

        coEvery { taxonomyDataSource.getTaxonomies() } returns
                RequestResult.Success(taxonomyResponse())

        loginRepository.login(TEST_EMAIL, TEST_PASSWORD)

        coVerifyOrder {
            taxonomyDataSource.getTaxonomies()
            taxonomyStorage.setProjectStatuses(projectsTaxonomyItems())
        }
        coVerify {
            taxonomyStorage.setProjectStatuses(projectsTaxonomyItems())
            taxonomyStorage.setPosition(positionsTaxonomyItems())
        }
    }

    @Test
    fun `When login, if login failed, then return error`() = runBlockingTest {
        val loginResponse = RequestResult.Error("Test error")

        coEvery { loginDataSource.login(any()) } returns loginResponse

        val result = loginRepository.login(TEST_EMAIL, TEST_PASSWORD)

        assertTrue(result is RequestResult.Error)
        assertTrue((result as RequestResult.Error).message == loginResponse.message)
    }

    @Test
    fun `When login, if login failed, then skip loading taxonomies`() = runBlockingTest {
        val loginResponse = RequestResult.Error("Test error")

        coEvery { loginDataSource.login(any()) } returns loginResponse

        loginRepository.login(TEST_EMAIL, TEST_PASSWORD)

        coVerify(inverse = true) {
            taxonomyDataSource.getTaxonomies()
            taxonomyStorage.setProjectStatuses(any())
            taxonomyStorage.setPosition(any())
        }
    }

    @Test
    fun `When login, if login success and load taxonomies failed, then return error`() = runBlockingTest {
        val taxonomyResponse = RequestResult.Error("Test error")


        coEvery { loginDataSource.login(any()) } returns RequestResult.Success(loginResponse())
        coEvery { taxonomyDataSource.getTaxonomies() } returns taxonomyResponse

        val result = loginRepository.login(TEST_EMAIL, TEST_PASSWORD)

        assertTrue(result is RequestResult.Error)
        assertTrue((result as RequestResult.Error).message == taxonomyResponse.message)
    }

    private fun loginResponse(): LoginResponse {
        return LoginResponse(
            id = "1",
            name = "Test",
            email = TEST_EMAIL,
            accessLevel = LoginResponse.AccessLevelResponse(
                "2",
                "Test access"
            ),
            token = "test_token"
        )
    }

    private fun userDataModel() = UserDataModel(
        id = "1",
        name = "Test",
        email = TEST_EMAIL,
        accessLevel = UserDataModel.AccessLevelDataModel("2", "Test access"),
        token = "test_token"
    )

    private fun emptyTaxonomyResponse() = TaxonomyResponse(emptyList(), emptyList())

    private fun taxonomyResponse() = TaxonomyResponse(
        listOf(
            TaxonomyResponse.TaxonomyItemResponse(1, "project 1"),
            TaxonomyResponse.TaxonomyItemResponse(2, "project 2"),
            TaxonomyResponse.TaxonomyItemResponse(3, "project 3")
        ),
        listOf(
            TaxonomyResponse.TaxonomyItemResponse(1, "position 1"),
            TaxonomyResponse.TaxonomyItemResponse(2, "position 2"),
            TaxonomyResponse.TaxonomyItemResponse(3, "position 3")
        )
    )

    private fun positionsTaxonomyItems(): List<TaxonomyItem> {
        return listOf(
            TaxonomyItem("1", "position 1"),
            TaxonomyItem("2", "position 2"),
            TaxonomyItem("3", "position 3")
        )
    }

    private fun projectsTaxonomyItems(): List<TaxonomyItem> {
        return listOf(
            TaxonomyItem("1", "project 1"),
            TaxonomyItem("2", "project 2"),
            TaxonomyItem("3", "project 3")
        )
    }
}