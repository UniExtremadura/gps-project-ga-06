package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class LastModified (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("value" ) var value : String? = null

)