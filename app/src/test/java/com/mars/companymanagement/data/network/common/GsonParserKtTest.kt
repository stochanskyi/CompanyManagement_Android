package com.mars.companymanagement.data.network.common

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class GsonParserKtTest {

    private val gson = Gson()

    @Test
    fun `When response as request result, if response success with string, then return Success request result with same string`() {
        val response = Response.success("Test response")

        val expectedResult = RequestResult.Success("Test response")
        val actualResult = response.asRequestResult(gson)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `When response as request result, if response success empty, then return Success request result empty`() {
        val response = Response.success(Unit)

        val expectedResult = RequestResult.Success(Unit)
        val actualResult = response.asRequestResult(gson)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `When response as request result, if response success null, then return unknown error`() {
        val response = Response.success(null)

        val result = response.asRequestResult(gson)

        assertTrue(result is RequestResult.UnknownError)
    }

    @Test
    fun `When response as request result, if response error with message, then return Error request result with message`() {
        val errorMessage = "Test message"
        val errorMessageJson = "{\"message\":\"$errorMessage\"}"

        val response = Response.error<Nothing>(500, errorMessageJson.toResponseBody(jsonMediaType))

        val expectedResult = RequestResult.Error("Wrong email or password")
        val actualResult = response.asRequestResult(gson)

        assertTrue(actualResult is RequestResult.Error)
        assertEquals(expectedResult.message, (actualResult as RequestResult.Error).message)
    }

    @Test
    fun `When response as request result, if response error without message, then return unknown error`() {
        val response = Response.error<Nothing>(500, "".toResponseBody(jsonMediaType))

        val result = response.asRequestResult(gson)

        assertTrue(result is RequestResult.UnknownError)
    }


    private val jsonMediaType: MediaType
        get() = "application/json; charset=utf-8".toMediaType()

}