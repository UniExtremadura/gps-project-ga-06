package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class TrendingQuery (
    @SerializedName("query" ) var query         : String?                  = null,
    @SerializedName("works" ) var trendingWorks : ArrayList<TrendingWork>  = arrayListOf(),
    @SerializedName("days"  ) var days          : Int?                     = null,
    @SerializedName("hours" ) var hours         : Int?                     = null
)