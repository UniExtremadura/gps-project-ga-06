package com.aseegpsproject.openbook.data.model

import java.io.Serializable

data class Work(
    val key: String,
    val title: String? = null,
    var description: String? = null,
    val authorNames: ArrayList<String>? = arrayListOf(),
    val authorKeys: ArrayList<String>? = arrayListOf(),
    val firstPublishYear: Int? = null,
    val coverPaths: ArrayList<String> = arrayListOf(),
    val subjects: ArrayList<String> = arrayListOf(),
    val numEditions: Int? = null,
) : Serializable
