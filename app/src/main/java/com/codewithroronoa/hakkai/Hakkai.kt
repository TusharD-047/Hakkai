package com.codewithroronoa.hakkai

import android.app.Application
import timber.log.Timber

class Hakkai : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}