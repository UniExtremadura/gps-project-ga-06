package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName

data class Authors (
    @SerializedName("author" ) var author : Author? = Author()
)