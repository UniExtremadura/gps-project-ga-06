package com.aseegpsproject.openbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aseegpsproject.openbook.data.model.UserWithWorks
import com.aseegpsproject.openbook.data.model.UserWorkCrossRef
import com.aseegpsproject.openbook.data.model.Work

@Dao
interface WorksDao {
    @Query("SELECT * FROM Work WHERE workKey = :workId LIMIT 1")
    suspend fun findById(workId: Long): Work

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(work: Work)

    @Delete
    suspend fun delete(work: Work)

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserWithWorks(userId: Long): UserWithWorks

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWithWorks(crossRef: UserWorkCrossRef)

    @Transaction
    suspend fun insertAndRelate(work: Work, userId: Long) {
        insert(work)
        insertUserWithWorks(UserWorkCrossRef(userId, work.workKey))
    }
}