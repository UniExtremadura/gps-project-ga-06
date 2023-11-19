package com.aseegpsproject.openbook.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aseegpsproject.openbook.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
    suspend fun getByUsername(username: String): User?

    @Insert
    suspend fun insert(user: User): Long
}