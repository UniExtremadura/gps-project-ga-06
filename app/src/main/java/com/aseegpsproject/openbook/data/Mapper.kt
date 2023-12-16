package com.aseegpsproject.openbook.data

import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.Rating
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

fun TrendingWork.toWork() = Work(
    workKey = this.key,
    title = this.title,
    authorNames = this.authorName,
    authorKeys = this.authorKey,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") }
        ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png")
)

fun Doc.toWork() = Work(
    workKey = this.key,
    title = this.title,
    authorNames = this.authorName,
    authorKeys = this.authorKey,
    firstPublishYear = this.firstPublishYear,
    coverPaths = this.coverI?.let { arrayListOf("https://covers.openlibrary.org/b/id/$it-M.jpg") }
        ?: arrayListOf("https://openlibrary.org/images/icons/avatar_book-sm.png")
)

fun Doc.toAuthor() = Author(
    authorKey = this.key,
    name = this.name,
    fullName = this.name,
    bio = null,
    birthDate = this.birthDate,
    deathDate = this.deathDate,
    photoPath = this.key.let { "https://covers.openlibrary.org/a/olid/$key-M.jpg?default=false" }
)

suspend fun Author.checkPhotoPath(): Boolean = withContext(Dispatchers.IO) {
    if (this@checkPhotoPath.photoPath != null) {
        val url = java.net.URL(this@checkPhotoPath.photoPath)
        val connection = url.openConnection() as java.net.HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()
        val code = connection.responseCode
        if (code == 404) {
            return@withContext false
        }
    }
    return@withContext true
}

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