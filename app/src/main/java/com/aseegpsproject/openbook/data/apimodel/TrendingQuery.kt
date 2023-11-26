package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class TrendingQuery (
    @SerializedName("works" ) var trendingWorks : ArrayList<TrendingWork>  = arrayListOf(),
)