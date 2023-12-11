package com.aseegpsproject.openbook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Work(
    @PrimaryKey(autoGenerate = false) val workKey: String,
    val title: String? = null,
    var description: String? = null,
    @ColumnInfo("author_names") val authorNames: ArrayList<String>? = arrayListOf(),
    @ColumnInfo("author_keys") val authorKeys: ArrayList<String>? = arrayListOf(),
    @ColumnInfo("first_publish_year") val firstPublishYear: Int? = null,
    @ColumnInfo("cover_paths") val coverPaths: ArrayList<String> = arrayListOf(),
    @ColumnInfo("rating") var rating: String? = null,
    @ColumnInfo("num_editions") val numEditions: Int? = null,
    @ColumnInfo("is_favorite") var isFavorite: Boolean = false
) : Serializable
