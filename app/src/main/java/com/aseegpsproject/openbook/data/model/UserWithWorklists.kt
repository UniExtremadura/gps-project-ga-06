package com.aseegpsproject.openbook.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithWorklists(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "work_list_id",
        associateBy = androidx.room.Junction(UserWorkListCrossRef::class)
    )
    val workLists: List<WorkList>
)
