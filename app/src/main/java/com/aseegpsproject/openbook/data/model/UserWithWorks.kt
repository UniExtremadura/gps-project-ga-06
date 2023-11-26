package com.aseegpsproject.openbook.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithWorks(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "workKey",
        associateBy = androidx.room.Junction(UserWorkCrossRef::class)
    )
    val works: List<Work>
)
