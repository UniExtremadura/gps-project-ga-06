package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class APIWork (
    @SerializedName("title"           ) var title          : String?            = null,
    @SerializedName("authors"         ) var authors        : ArrayList<Authors> = arrayListOf(),
    @SerializedName("key"             ) var key            : String?            = null,
    @SerializedName("description"     ) var description    : String?            = null,
    @SerializedName("subjects"        ) var subjects       : ArrayList<String>  = arrayListOf(),
    @SerializedName("covers"          ) var covers         : ArrayList<Int>     = arrayListOf(),
)

data class Authors (
    @SerializedName("author" ) var workAuthor : WorkAuthor? = WorkAuthor()
)

data class WorkAuthor (
    @SerializedName("key" ) var key : String? = null
)