package com.aseegpsproject.openbook.data.model

data class Edition (
    val id: Long?,
    val title: String,
    val author: String,
    val year: String,
    val description: String,
    val isbn: String,
    val coverImage: Int
)
