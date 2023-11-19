package com.aseegpsproject.openbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.database.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class OpenBookDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: OpenBookDatabase? = null

        fun getInstance(context: Context): OpenBookDatabase? {
            if (INSTANCE == null) {
                synchronized(OpenBookDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context, OpenBookDatabase::class.java, "openbook.db")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}