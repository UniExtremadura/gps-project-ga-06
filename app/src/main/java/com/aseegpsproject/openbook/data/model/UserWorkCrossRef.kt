package com.aseegpsproject.openbook.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["userId", "workKey"],
    tableName = "user_work_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Work::class,
            parentColumns = ["workKey"],
            childColumns = ["workKey"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserWorkCrossRef(
    val userId: Long,
    val workKey: String
)