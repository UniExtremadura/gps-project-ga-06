package com.aseegpsproject.openbook.data

import com.aseegpsproject.openbook.data.apimodel.APIWork
import com.aseegpsproject.openbook.data.apimodel.Author
import com.aseegpsproject.openbook.data.apimodel.Authors
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.model.Work

fun APIWork.toWork() = Work(
    key = this.key,
    title = this.title,
    description = this.description,
    authors = this.authors.map { it.toStr() } as ArrayList<String>,
    firstPublishYear = this.firstPublishDate?.substring(0, 4)?.toInt() ?: 2002,
    coverPaths = this.covers.map { "https://covers.openlibrary.org/b/id/$it-M.jpg" } as ArrayList<String>,
    subjects = this.subjects,
)

fun TrendingWork.toWork() = Work(
    key = this.key,
    title = this.title,
    authors = this.authorName,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") } ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png"),
    subjects = arrayListOf(),
)

fun Author.toStr() = this.key ?: "Unknown"

fun Authors.toStr() = this.author?.toStr() ?: "Unknown"

fun Doc.toWork() = Work(
    key = this.key,
    title = this.title,
    authors = this.authorName,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") } ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png"),
    subjects = arrayListOf(),
)