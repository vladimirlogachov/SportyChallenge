package com.sporty.challenge

import android.app.Application
import com.sporty.challenge.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SportyChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(androidContext = this@SportyChallengeApp)
            androidLogger(level = Level.INFO)
            modules(modules = appComponent)
        }
    }

}
