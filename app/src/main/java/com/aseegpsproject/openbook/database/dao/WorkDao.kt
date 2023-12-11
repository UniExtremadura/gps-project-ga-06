package com.aseegpsproject.openbook.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aseegpsproject.openbook.data.model.UserWithWorks
import com.aseegpsproject.openbook.data.model.UserWorkCrossRef
import com.aseegpsproject.openbook.data.model.Work

@Dao
interface WorkDao {
    @Query("SELECT * FROM Work")
    fun getWorks(): LiveData<List<Work>>

    @Query("SELECT count(*) FROM Work")
    suspend fun getNumberOfWorks(): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(works: List<Work>)

    @Query("SELECT * FROM Work WHERE workKey = :workId LIMIT 1")
    suspend fun findById(workId: Long): Work

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(work: Work)

    @Delete
    suspend fun delete(work: Work)

    @Update
    suspend fun update(work: Work)

    @Delete
    suspend fun delete(userShow: UserWorkCrossRef)

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserWithWorks(userId: Long): LiveData<UserWithWorks>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWork(crossRef: UserWorkCrossRef)

    @Transaction
    suspend fun insertAndRelate(work: Work, userId: Long) {
        insert(work)
        insertUserWork(UserWorkCrossRef(userId, work.workKey))
    }
}