package com.mars.companymanagement.data.database.feature.user.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    val userName: String,
    val email: String,
    val password: String,
    val accessLevelId: Int
)