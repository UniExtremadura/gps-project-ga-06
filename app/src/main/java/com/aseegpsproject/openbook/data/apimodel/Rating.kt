package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class Rating (
    @SerializedName("summary" ) var summary : Summary? = Summary(),
    @SerializedName("counts"  ) var counts  : Counts?  = Counts()
)

data class Counts (
    @SerializedName("1" ) var one : Int? = null,
    @SerializedName("2" ) var two : Int? = null,
    @SerializedName("3" ) var three : Int? = null,
    @SerializedName("4" ) var four : Int? = null,
    @SerializedName("5" ) var five : Int? = null
)

data class Summary (
    @SerializedName("average"  ) var average  : Double? = null,
    @SerializedName("count"    ) var count    : Int?    = null,
    @SerializedName("sortable" ) var sortable : Double? = null
)