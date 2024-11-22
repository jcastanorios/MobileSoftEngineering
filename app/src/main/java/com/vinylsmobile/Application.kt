package com.vinylsmobile

import android.app.Application
import com.squareup.leakcanary.core.BuildConfig
import leakcanary.LeakCanary

enum class UserRole {
    VISITOR, COLLECTOR
}

class Application : Application() {
    private var userRole: UserRole? = null

    fun getUserRole(): UserRole? {
        return userRole
    }

    fun setUserRole(someVariable: UserRole) {
        this.userRole = someVariable
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
        }
    }
}
