package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.annotations.SerializedName


data class APIWork (
  @SerializedName("key"                )    var key              : String?             = null,
  @SerializedName("title"              )    var title            : String?             = null,
  @SerializedName("description"        )    var description      : String?             = null,
  @SerializedName("authors"            )    var authors          : ArrayList<Authors>  = arrayListOf(),
  @SerializedName("first_publish_date" )    var firstPublishDate : String?             = null,
  @SerializedName("covers"             )    var covers           : ArrayList<Int>      = arrayListOf(),
  @SerializedName("links"              )    var links            : ArrayList<Links>    = arrayListOf(),
  @SerializedName("subjects"           )    var subjects         : ArrayList<String>   = arrayListOf(),
  @SerializedName("subject_places"     )    var subjectPlaces    : ArrayList<String>   = arrayListOf(),
  @SerializedName("subject_people"     )    var subjectPeople    : ArrayList<String>   = arrayListOf(),
  @SerializedName("excerpts"           )    var excerpts         : ArrayList<Excerpts> = arrayListOf(),
  @SerializedName("type"               )    var type             : Type?               = Type(),
  @SerializedName("revision"           )    var revision         : Int?                = null,
  @SerializedName("latest_revision"    )    var latestRevision   : Int?                = null,
  @SerializedName("created"            )    var created          : Created?            = Created(),
  @SerializedName("last_modified"      )    var lastModified     : LastModified?       = LastModified()
)