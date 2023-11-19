package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class Links (

  @SerializedName("url"   ) var url   : String? = null,
  @SerializedName("title" ) var title : String? = null,
  @SerializedName("type"  ) var type  : Type?   = Type()

)