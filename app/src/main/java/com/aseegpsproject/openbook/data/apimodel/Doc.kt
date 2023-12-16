package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class Doc(
    @SerializedName("key") var key: String,
    @SerializedName("title") var title: String? = null,
    @SerializedName("first_publish_year") var firstPublishYear: Int? = null,
    @SerializedName("cover_i") var coverI: Int? = null,
    @SerializedName("author_name") var authorName: ArrayList<String> = arrayListOf(),
    @SerializedName("author_key") var authorKey: ArrayList<String> = arrayListOf(),

    @SerializedName("name") var name: String? = null,
    @SerializedName("alternate_names") var alternateNames: ArrayList<String> = arrayListOf(),
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("death_date") var deathDate: String? = null,
    @SerializedName("top_work") var topWork: String? = null,
    @SerializedName("work_count") var workCount: Int? = null
)