package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["user_id", "author_key"],
    tableName = "user_author_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Author::class,
            parentColumns = ["author_key"],
            childColumns = ["author_key"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserAuthorCrossRef(
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("author_key") val authorKey: String
)
