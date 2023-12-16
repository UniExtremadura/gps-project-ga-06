package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Works_list")
data class WorkList(
    @ColumnInfo("work_list_id") @PrimaryKey(autoGenerate = true) var workListId: Long = 0L,
    val name: String,
    var works: List<Work>
) : Serializable


