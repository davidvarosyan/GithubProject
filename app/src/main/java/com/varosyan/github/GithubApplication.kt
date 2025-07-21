package com.varosyan.github

import android.app.Application
import android.util.Log
import com.varosyan.connecter.getAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin


class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val defaultUEH = Thread.getDefaultUncaughtExceptionHandler()


        // set your own handler
        Thread.setDefaultUncaughtExceptionHandler { thread, e -> // 1) Log/report the exception (e.g., to Crashlytics, your server, or local file)
            Log.e("MyApp", "Uncaught exception in thread " + thread.name, e)

            // 2) (Optional) Show your own error UI or restart logic
            //    e.g. startActivity(new Intent(getApplicationContext(), ErrorActivity.class)
            //             .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            // 3) Delegate to default handler to kill the process
            defaultUEH?.uncaughtException(thread, e)
        }
        startKoin {
            androidContext(this@GithubApplication)
            loadKoinModules(getAppModules())
        }
    }
}