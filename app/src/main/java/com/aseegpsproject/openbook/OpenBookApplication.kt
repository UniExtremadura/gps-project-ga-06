package com.aseegpsproject.openbook

import android.app.Application
import com.aseegpsproject.openbook.util.AppContainer

class OpenBookApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}