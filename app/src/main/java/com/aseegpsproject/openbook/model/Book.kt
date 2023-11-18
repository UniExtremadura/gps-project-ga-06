package com.aseegpsproject.openbook.model

data class Book(
    val id: Long?,
    val title: String,
    val author: String,
    val year: String,
    val description: String,
    val isbn: String,
    val coverImage: Int
)
