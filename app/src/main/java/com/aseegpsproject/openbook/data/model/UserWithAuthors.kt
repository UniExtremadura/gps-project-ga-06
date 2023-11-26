package com.aseegpsproject.openbook.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithAuthors(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "authorKey",
        associateBy = androidx.room.Junction(UserAuthorCrossRef::class)
    )
    val authors: List<Author>
)
