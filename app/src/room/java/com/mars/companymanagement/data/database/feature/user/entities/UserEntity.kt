package com.mars.companymanagement.data.database.feature.user.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "user", foreignKeys = [ForeignKey(
        entity = AccessLevelEntity::class,
        parentColumns = arrayOf("levelId"),
        childColumns = arrayOf("accessLevelId"),
        onDelete = CASCADE
    )]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val userName: String,
    val email: String,
    val password: String,
    @ColumnInfo(name = "accessLevelId")
    val accessLevelId: Int
)