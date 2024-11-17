package com.vinylsmobile

import android.app.Application
import com.squareup.leakcanary.core.BuildConfig
import leakcanary.LeakCanary

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
        }
    }
}
