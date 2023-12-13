package com.aseegpsproject.openbook.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithAuthors(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "author_key",
        associateBy = androidx.room.Junction(UserAuthorCrossRef::class)
    )
    val authors: List<Author>
)
