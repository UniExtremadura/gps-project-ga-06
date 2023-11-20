package com.aseegpsproject.openbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long?,
    val username: String,
    val password: String
) : Serializable