package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["user_id", "work_key"],
    tableName = "user_work_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Work::class,
            parentColumns = ["work_key"],
            childColumns = ["work_key"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserWorkCrossRef(
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("work_key") val workKey: String
)