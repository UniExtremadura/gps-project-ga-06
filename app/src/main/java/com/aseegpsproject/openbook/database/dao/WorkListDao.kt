package com.aseegpsproject.openbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aseegpsproject.openbook.data.model.UserWithWorklists
import com.aseegpsproject.openbook.data.model.UserWorklistCrossRef
import com.aseegpsproject.openbook.data.model.WorkList

@Dao
interface WorkListDao {
    @Query("SELECT * FROM Works_list WHERE work_list_id = :id")
    fun getById(id: Int): WorkList

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workList: WorkList): Long

    @Update
    fun update(workList: WorkList)

    @Delete
    fun delete(workList: WorkList)

    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :userId")
    suspend fun getUserWithWorkLists(userId: Long): UserWithWorklists

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWithWorkLists(crossRef: UserWorklistCrossRef)

    @Transaction
    suspend fun insertAndRelate(workList: WorkList, userId: Long) {
        insert(workList)
        workList.workListId?.let { UserWorklistCrossRef(userId, it) }
            ?.let { insertUserWithWorkLists(it) }
    }

    @Transaction
    suspend fun updateAndRelate(workList: WorkList, userId: Long) {
        update(workList)
        workList.workListId?.let { UserWorklistCrossRef(userId, it) }
            ?.let { insertUserWithWorkLists(it) }
    }
}