package com.aseegpsproject.openbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val username: String,
    val password: String
) : Serializable