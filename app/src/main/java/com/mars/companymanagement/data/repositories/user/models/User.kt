package com.mars.companymanagement.data.repositories.user.models

import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevel

data class User(
    val id: String,
    val name: String,
    val email: String,
    val accessLevel: AccessLevel
)