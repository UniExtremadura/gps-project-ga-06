package com.aseegpsproject.openbook.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["userId", "authorKey"],
    tableName = "user_author_cross_ref",
    foreignKeys = [
        ForeignKey(
            entity = Author::class,
            parentColumns = ["authorKey"],
            childColumns = ["authorKey"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserAuthorCrossRef(
    val userId: Long,
    val authorKey: String
)
