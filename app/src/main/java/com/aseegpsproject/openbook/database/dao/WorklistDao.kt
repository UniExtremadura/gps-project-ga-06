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
import com.aseegpsproject.openbook.data.model.Worklist

@Dao
interface WorklistDao {
    @Query("SELECT * FROM Works_list WHERE worklistId = :id")
    fun getById(id: Int): Worklist

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workList: Worklist): Long

    @Update
    fun update(workList: Worklist)

    @Delete
    fun delete(workList: Worklist)

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserWithWorkLists(userId: Long): UserWithWorklists

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWithWorkLists(crossRef: UserWorklistCrossRef)

    @Transaction
    suspend fun insertAndRelate(workList: Worklist, userId: Long) {
        insert(workList)
        workList.worklistId?.let { UserWorklistCrossRef(userId, it) }
            ?.let { insertUserWithWorkLists(it) }
    }

    @Transaction
    suspend fun updateAndRelate(workList: Worklist, userId: Long) {
        update(workList)
        workList.worklistId?.let { UserWorklistCrossRef(userId, it) }
            ?.let { insertUserWithWorkLists(it) }
    }
}