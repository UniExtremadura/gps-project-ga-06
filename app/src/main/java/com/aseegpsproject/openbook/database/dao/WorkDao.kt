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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(works: List<Work>)

    @Update
    suspend fun updateAll(works: List<Work>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(work: Work)

    @Delete
    suspend fun delete(work: Work)

    @Update
    suspend fun update(work: Work)

    @Delete
    suspend fun delete(userWork: UserWorkCrossRef)

    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun getUserWithWorks(userId: Long): LiveData<UserWithWorks>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWork(crossRef: UserWorkCrossRef)

    @Transaction
    suspend fun insertAndRelate(work: Work, userId: Long) {
        insert(work)
        insertUserWork(UserWorkCrossRef(userId, work.workKey))
    }

    @Transaction
    suspend fun insertAllAndRelate(works: List<Work>, userId: Long) {
        insertAll(works)
        works.forEach { work ->
            insertUserWork(UserWorkCrossRef(userId, work.workKey))
        }
    }
}