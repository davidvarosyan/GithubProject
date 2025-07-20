package com.varosyan.github

import android.app.Application
import com.varosyan.connecter.getAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubApplication)
            loadKoinModules(getAppModules())
        }
    }
}