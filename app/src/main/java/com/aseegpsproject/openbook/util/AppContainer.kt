package com.aseegpsproject.openbook.util

import android.content.Context
import androidx.preference.PreferenceManager
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.database.OpenBookDatabase

class AppContainer(context: Context) {
    private val networkService = getNetworkService()
    private val db = OpenBookDatabase.getInstance(context)
    var repository = db?.let {
        Repository(
            it.userDao(),
            it.workDao(),
            it.authorDao(),
            it.workListDao(),
            networkService
        )
    }

    init {
        val prefs = context.let { PreferenceManager.getDefaultSharedPreferences(it) }
        if (prefs != null) {
            val trendingFreq = prefs.getString("trending", "daily") ?: "daily"
            repository?.setTrendingFreq(trendingFreq)
        }
    }
}