package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "author")
data class Author(
    @ColumnInfo("author_key") @PrimaryKey(autoGenerate = false) val authorKey: String,
    val name: String? = null,
    @ColumnInfo("full_name") val fullName: String? = null,
    @ColumnInfo("birth_date") val birthDate: String? = null,
    @ColumnInfo("death_date") val deathDate: String? = null,
    @ColumnInfo("photo_path") var photoPath: String? = null,
    val wikipedia: String? = null,
    var bio: String? = null,
    @ColumnInfo("num_works") val numWorks: Int? = null,
    @ColumnInfo("is_favorite") var isFavorite: Boolean = false
) : Serializable
