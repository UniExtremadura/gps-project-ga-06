package com.aseegpsproject.openbook.data

import androidx.lifecycle.lifecycleScope
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.apimodel.APIAuthor
import com.aseegpsproject.openbook.data.apimodel.Authors
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.apimodel.WorkAuthor
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.Work

fun TrendingWork.toWork() = Work(
    key = this.key,
    title = this.title,
    authorNames = this.authorName,
    authorKeys = this.authorKey,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") } ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png"),
    subjects = arrayListOf(),
)

fun WorkAuthor.toStr() = this.key ?: "Unknown"

fun Authors.toStr() = this.workAuthor?.toStr() ?: "Unknown"

fun Doc.toWork() = Work(
    key = this.key,
    title = this.title,
    authorNames = this.authorName,
    authorKeys = this.authorKey,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") } ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png"),
    subjects = arrayListOf()
)

fun Doc.toAuthor() = Author(
    authorKey = this.key,
    name = this.name,
    fullName = this.name,
    bio = "",
    birthDate = this.birthDate,
    deathDate = this.deathDate,
    photoPath = this.key.let { "https://covers.openlibrary.org/a/olid/$it-M.jpg"  }
)

fun APIAuthor.toAuthor() = Author(
    authorKey = this.key,
    name = this.name,
    fullName = this.personalName,
    bio = this.bio,
    birthDate = this.birthDate,
    deathDate = this.deathDate,
    photoPath = this.photos[0].let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
)