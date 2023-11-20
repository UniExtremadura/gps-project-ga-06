package com.aseegpsproject.openbook.data.model

data class Work(
    val key: String? = null,
    val title: String? = null,
    val description: String? = null,
    val authorNames: ArrayList<String> = arrayListOf(),
    val authorKeys: ArrayList<String>,
    val firstPublishYear: Int? = null,
    val coverPaths: ArrayList<String> = arrayListOf(),
    val subjects: ArrayList<String> = arrayListOf(),
    val numEditions: Int? = null,
)
