package com.mars.companymanagement.data.network.taxonomy

import com.mars.companymanagement.data.network.taxonomy.models.TaxonomyResponse
import retrofit2.Response
import retrofit2.http.GET

interface TaxonomyApi {
    @GET("taxonomy")
    suspend fun getTaxonomy(): Response<TaxonomyResponse>
}