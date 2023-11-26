package com.aseegpsproject.openbook.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "worklistId"],
    tableName = "user_worklist_cross_ref",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Worklist::class,
            parentColumns = ["worklistId"],
            childColumns = ["worklistId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class UserWorklistCrossRef(
    val userId: Long,
    val worklistId: Long
)
