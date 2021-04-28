package com.mars.companymanagement.data.network.taxonomy.models

import com.google.gson.annotations.SerializedName

data class TaxonomyResponse(
    @SerializedName("projectStatuses")
    val projectStatuses: List<TaxonomyItemResponse>,
    @SerializedName("positions")
    val positions: List<TaxonomyItemResponse>
) {
    data class TaxonomyItemResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}