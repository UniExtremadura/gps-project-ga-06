package com.aseegpsproject.openbook.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithWorklists(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "worklistId",
        associateBy = androidx.room.Junction(UserWorklistCrossRef::class)
    )
    val worklists: List<Worklist>
)
