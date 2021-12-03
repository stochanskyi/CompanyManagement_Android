package com.mars.companymanagement.data.common

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mars.companymanagement.R
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringProviderImplTest {

    lateinit var context: Context

    private lateinit var stringProvider: StringProvider

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        stringProvider = StringProviderImpl(context)
    }

    @Test
    fun `When get string from resources, if single string resource, return string`() {
        val expectedResult = context.getString(R.string.test_single_string)
        val actualResult = stringProvider.getString(R.string.test_single_string)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `When get string from resources, if string resource with params, return concated string`() {
        val param1 = "Test1"
        val param2 = "Test2"

        val expectedResult = context.getString(R.string.test_parametrized_string, param1, param2)

        val actualResult = stringProvider.getString(R.string.test_parametrized_string, param1, param2)

        assertEquals(expectedResult, actualResult)
    }

}