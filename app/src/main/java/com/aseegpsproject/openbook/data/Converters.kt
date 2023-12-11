package com.aseegpsproject.openbook.data

import androidx.room.TypeConverter
import com.aseegpsproject.openbook.data.model.Work
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        return try {
            val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
            Gson().fromJson(value, listType)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromWorksListString(value: String?): List<Work> {
        return try {
            val listType: Type = object : TypeToken<List<Work>>() {}.type
            Gson().fromJson(value, listType)
        } catch (e: Exception) {
            listOf()
        }
    }

    @TypeConverter
    fun fromWorksList(works: List<Work>): String {
        val gson = Gson()
        return gson.toJson(works)
    }
}