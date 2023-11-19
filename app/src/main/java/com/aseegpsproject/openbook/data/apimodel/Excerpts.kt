package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class Excerpts (

  @SerializedName("comment" ) var comment : String? = null,
  @SerializedName("author"  ) var author  : Author? = Author(),
  @SerializedName("excerpt" ) var excerpt : String? = null

)