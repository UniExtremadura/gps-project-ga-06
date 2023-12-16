package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.JsonDeserializer
import com.google.gson.annotations.SerializedName


data class APIAuthor(
    @SerializedName("personal_name") var personalName: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("death_date") var deathDate: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("photos") var photos: ArrayList<Int> = arrayListOf(),
    @SerializedName("alternate_names") var alternateNames: ArrayList<String> = arrayListOf(),
    @SerializedName("fuller_name") var fullerName: String? = null,
    @SerializedName("key") var key: String
)

class APIAuthorDeserializer : JsonDeserializer<APIAuthor> {
    override fun deserialize(
        json: com.google.gson.JsonElement?,
        typeOfT: java.lang.reflect.Type?,
        context: com.google.gson.JsonDeserializationContext?
    ): APIAuthor {
        val jsonObject = json?.asJsonObject
        val personalName = jsonObject?.get("personal_name")?.asString
        val name = jsonObject?.get("name")?.asString
        val birthDate = jsonObject?.get("birth_date")?.asString
        val deathDate = jsonObject?.get("death_date")?.asString
        // Check if bio is string or object and store as string
        val bio = if (jsonObject?.get("bio")?.isJsonObject == true) {
            jsonObject.get("bio").asJsonObject.get("value").asString
        } else {
            jsonObject?.get("bio")?.asString
        }
        val photos = jsonObject?.get("photos")?.asJsonArray
        val alternateNames = jsonObject?.get("alternate_names")?.asJsonArray
        val fullerName = jsonObject?.get("fuller_name")?.asString
        val key = jsonObject?.get("key")?.asString

        val photosList = arrayListOf<Int>()
        photos?.forEach {
            photosList.add(it.asInt)
        }

        val alternateNamesList = arrayListOf<String>()
        alternateNames?.forEach {
            alternateNamesList.add(it.asString)
        }

        return APIAuthor(
            personalName,
            name,
            birthDate,
            deathDate,
            bio,
            photosList,
            alternateNamesList,
            fullerName,
            key!!
        )
    }
}