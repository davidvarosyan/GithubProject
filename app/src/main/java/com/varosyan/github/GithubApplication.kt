package com.varosyan.github

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val defaultUEH = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, e -> // 1) Log/report the exception (e.g., to Crashlytics, your server, or local file)
            Log.e("MyApp", "Uncaught exception in thread " + thread.name, e)
            defaultUEH?.uncaughtException(thread, e)
        }
    }
}