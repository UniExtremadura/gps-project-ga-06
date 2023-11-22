package com.aseegpsproject.openbook.data

import com.aseegpsproject.openbook.data.apimodel.APIAuthor
import com.aseegpsproject.openbook.data.apimodel.APIWork
import com.aseegpsproject.openbook.data.apimodel.Authors
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.Rating
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.Work
import kotlin.math.roundToInt

fun APIWork.toWork() = this.key?.let {
    Work(
        key = it,
        title = this.title,
        authorNames = null,
        authorKeys = this.authors.map { it.toKeyStr() } as ArrayList<String>,
        firstPublishYear = null,
        coverPaths = this.covers.map { "https://covers.openlibrary.org/b/id/$it-M.jpg" } as ArrayList<String>,
        subjects = this.subjects,
        description = this.description
    )
}

fun Authors.toKeyStr() = this.workAuthor?.key ?: ""

fun TrendingWork.toWork() = Work(
    key = this.key,
    title = this.title,
    authorNames = this.authorName,
    authorKeys = this.authorKey,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") } ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png"),
    subjects = arrayListOf(),
)

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

fun Rating.toStr() = generateString(this)

fun generateString(rating: Rating): String {
    val average = rating.summary?.average
    val percentage = average?.div(5)?.times(100)
    val percentageRounded = percentage?.let { (it * 100.0).roundToInt() / 100.0 }
    val averageRounded = average?.let { (it * 100.0).roundToInt() / 100.0 }
    return if (averageRounded != null && percentageRounded != null) {
        "$averageRounded/5 ($percentageRounded%)"
    } else {
        "N/A"
    }
}