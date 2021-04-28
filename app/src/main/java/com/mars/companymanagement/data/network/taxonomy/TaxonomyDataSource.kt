package com.mars.companymanagement.data.network.taxonomy

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.taxonomy.models.TaxonomyResponse
import javax.inject.Inject

interface TaxonomyDataSource {
    suspend fun getTaxonomies(): RequestResult<TaxonomyResponse>
}

class TaxonomyDataSourceImpl @Inject constructor(
    private val taxonomyApi: TaxonomyApi,
    private val gson: Gson
) : TaxonomyDataSource {
    override suspend fun getTaxonomies(): RequestResult<TaxonomyResponse> {
        return taxonomyApi.getTaxonomy().asRequestResult(gson)
    }

}