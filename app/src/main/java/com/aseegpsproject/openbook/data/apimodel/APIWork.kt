package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class APIWork (
  @SerializedName("key"                )    var key              : String?             = null,
  @SerializedName("title"              )    var title            : String?             = null,
  @SerializedName("description"        )    var description      : String?             = null,
  @SerializedName("authors"            )    var authors          : ArrayList<Authors>  = arrayListOf(),
  @SerializedName("first_publish_date" )    var firstPublishDate : String?             = null,
  @SerializedName("covers"             )    var covers           : ArrayList<Int>      = arrayListOf(),
  @SerializedName("subjects"           )    var subjects         : ArrayList<String>   = arrayListOf(),
)