package  com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class TrendingWork (
    @SerializedName("key"                ) var key              : String?           = null,
    @SerializedName("title"              ) var title            : String?           = null,
    @SerializedName("edition_count"      ) var editionCount     : Int?              = null,
    @SerializedName("first_publish_year" ) var firstPublishYear : Int?              = null,
    @SerializedName("has_fulltext"       ) var hasFulltext      : Boolean?          = null,
    @SerializedName("public_scan_b"      ) var publicScanB      : Boolean?          = null,
    @SerializedName("cover_edition_key"  ) var coverEditionKey  : String?           = null,
    @SerializedName("cover_i"            ) var coverI           : Int?              = null,
    @SerializedName("language"           ) var language         : ArrayList<String> = arrayListOf(),
    @SerializedName("author_key"         ) var authorKey        : ArrayList<String> = arrayListOf(),
    @SerializedName("author_name"        ) var authorName       : ArrayList<String> = arrayListOf()
)