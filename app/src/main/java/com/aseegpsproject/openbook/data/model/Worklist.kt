package com.aseegpsproject.openbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Works_list")
data class Worklist(
    @PrimaryKey(autoGenerate = true) var worklistId: Long?,
    val name: String,
    var works: List<Work>
) : Serializable


