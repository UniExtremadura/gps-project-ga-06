package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @ColumnInfo("user_id") @PrimaryKey(autoGenerate = true) val userId: Long?,
    val username: String,
    val password: String
) : Serializable