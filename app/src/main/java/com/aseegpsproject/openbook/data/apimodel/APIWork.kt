package com.aseegpsproject.openbook.data.apimodel

import com.google.gson.JsonDeserializer
import com.google.gson.annotations.SerializedName


data class APIWork(
    @SerializedName("title") var title: String? = null,
    @SerializedName("authors") var authors: ArrayList<Authors> = arrayListOf(),
    @SerializedName("key") var key: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("subjects") var subjects: ArrayList<String> = arrayListOf(),
    @SerializedName("covers") var covers: ArrayList<Int> = arrayListOf(),
)

data class Authors(
    @SerializedName("author") var workAuthor: WorkAuthor? = WorkAuthor()
)

data class WorkAuthor(
    @SerializedName("key") var key: String? = null
)

class APIWorkDeserializer : JsonDeserializer<APIWork> {
    override fun deserialize(
        json: com.google.gson.JsonElement?,
        typeOfT: java.lang.reflect.Type?,
        context: com.google.gson.JsonDeserializationContext?
    ): APIWork {
        val jsonObject = json?.asJsonObject
        val title = jsonObject?.get("title")?.asString
        val authors = jsonObject?.get("authors")?.asJsonArray
        val key = jsonObject?.get("key")?.asString
        // Check if description is string or object and store as string
        val description = if (jsonObject?.get("description")?.isJsonObject == true) {
            jsonObject.get("description").asJsonObject.get("value").asString
        } else {
            jsonObject?.get("description")?.asString
        }
        val subjects = jsonObject?.get("subjects")?.asJsonArray
        val covers = jsonObject?.get("covers")?.asJsonArray

        val authorsList = arrayListOf<Authors>()
        authors?.forEach {
            val author = it.asJsonObject.get("author").asJsonObject.get("key").asString
            authorsList.add(Authors(WorkAuthor(author)))
        }

        val subjectsList = arrayListOf<String>()
        subjects?.forEach {
            subjectsList.add(it.asString)
        }

        val coversList = arrayListOf<Int>()
        covers?.forEach {
            coversList.add(it.asInt)
        }

        return APIWork(
            title,
            authorsList,
            key,
            description,
            subjectsList,
            coversList
        )
    }
}