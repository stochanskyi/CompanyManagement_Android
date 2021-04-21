package com.mars.companymanagement.data.storages

data class UserDataModel(
    val id: String,
    val name: String,
    val email: String,
    val accessLevel: AccessLevel,
    val token: String
) {
    data class AccessLevel(
        val id: String,
        val name: String
    )
}