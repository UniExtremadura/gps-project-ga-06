package com.aseegpsproject.openbook.data.model

data class Work(
    val key: String? = null,
    val title: String? = null,
    val description: String? = null,
    val authors: ArrayList<String> = arrayListOf(),
    val firstPublishYear: Int? = null,
    val coverPaths: ArrayList<String> = arrayListOf(),
    val subjects: ArrayList<String> = arrayListOf(),
    val subjectPlaces: ArrayList<String> = arrayListOf(),
    val subjectPeople: ArrayList<String> = arrayListOf()
)
