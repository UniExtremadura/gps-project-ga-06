package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class SearchQuery (
  @SerializedName("numFound"      ) var numFound      : Int?            = null,
  @SerializedName("start"         ) var start         : Int?            = null,
  @SerializedName("docs"          ) var docs          : ArrayList<Doc>  = arrayListOf()
)