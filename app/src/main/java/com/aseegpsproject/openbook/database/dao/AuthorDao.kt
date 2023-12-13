package com.aseegpsproject.openbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.UserAuthorCrossRef
import com.aseegpsproject.openbook.data.model.UserWithAuthors

@Dao
interface AuthorDao {
    @Query("SELECT * FROM author WHERE author_key = :authorId LIMIT 1")
    suspend fun findById(authorId: Long): Author

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: Author)

    @Delete
    suspend fun delete(author: Author)

    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :userId")
    suspend fun getUserWithAuthors(userId: Long): UserWithAuthors

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserWithAuthors(crossRef: UserAuthorCrossRef)

    @Transaction
    suspend fun insertAndRelate(author: Author, userId: Long) {
        insert(author)
        insertUserWithAuthors(UserAuthorCrossRef(userId, author.authorKey))
    }
}