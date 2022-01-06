package com.mars.companymanagement.data.database.feature.user.entities

import androidx.room.Embedded

data class UserWithAccessLevelEntity(
    @Embedded val user: UserEntity,
    @Embedded val accessLevel: AccessLevelEntity
)