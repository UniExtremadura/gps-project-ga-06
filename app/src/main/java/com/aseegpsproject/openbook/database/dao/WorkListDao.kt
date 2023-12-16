package com.aseegpsproject.openbook.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aseegpsproject.openbook.data.model.UserWithWorklists
import com.aseegpsproject.openbook.data.model.UserWorkListCrossRef
import com.aseegpsproject.openbook.data.model.WorkList

@Dao
interface WorkListDao {
    @Query("SELECT * FROM Works_list WHERE work_list_id = :workListId")
    fun getById(workListId: Long): LiveData<WorkList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workList: WorkList): Long

    @Update
    suspend fun update(workList: WorkList)

    @Delete
    suspend fun delete(workList: WorkList)

    @Delete
    suspend fun delete(userWorklist: UserWorkListCrossRef)

    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun getUserWithWorkLists(userId: Long): LiveData<UserWithWorklists>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWithWorkLists(crossRef: UserWorkListCrossRef)

    @Transaction
    suspend fun insertAndRelate(workList: WorkList, userId: Long) {
        val workListId = insert(workList)
        insertUserWithWorkLists(UserWorkListCrossRef(userId, workListId))
    }

    @Transaction
    suspend fun updateAndRelate(workList: WorkList, userId: Long) {
        update(workList)
        insertUserWithWorkLists(UserWorkListCrossRef(userId, workList.workListId))
    }
}