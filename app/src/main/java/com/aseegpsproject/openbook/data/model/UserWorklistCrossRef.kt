package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["user_id", "work_list_id"],
    tableName = "user_worklist_cross_ref",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = WorkList::class,
            parentColumns = ["work_list_id"],
            childColumns = ["work_list_id"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class UserWorklistCrossRef(
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("work_list_id") val workListId: Long
)
