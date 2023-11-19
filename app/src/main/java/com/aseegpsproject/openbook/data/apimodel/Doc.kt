package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class Doc (
  @SerializedName("key"                                  ) var key                              : String?           = null,
  @SerializedName("title"                                ) var title                            : String?           = null,
  @SerializedName("first_publish_year"                   ) var firstPublishYear                 : Int?              = null,
  @SerializedName("cover_i"                              ) var coverI                           : Int?              = null,
  @SerializedName("author_name"                          ) var authorName                       : ArrayList<String> = arrayListOf(),
)