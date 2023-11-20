package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class APIAuthor (
    @SerializedName("personal_name"   ) var personalName   : String?           = null,
    @SerializedName("name"            ) var name           : String?           = null,
    @SerializedName("birth_date"      ) var birthDate      : String?           = null,
    @SerializedName("death_date"      ) var deathDate      : String?           = null,
    @SerializedName("bio"             ) var bio            : String?           = null,
    @SerializedName("photos"          ) var photos         : ArrayList<Int>    = arrayListOf(),
    @SerializedName("alternate_names" ) var alternateNames : ArrayList<String> = arrayListOf(),
    @SerializedName("fuller_name"     ) var fullerName     : String?           = null,
    @SerializedName("key"             ) var key            : String
)