package com.aseegpsproject.openbook.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithWorks(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "work_key",
        associateBy = androidx.room.Junction(UserWorkCrossRef::class)
    )
    val works: List<Work>
)
