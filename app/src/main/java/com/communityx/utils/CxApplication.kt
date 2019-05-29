package com.communityx.utils

import android.app.Application

class CxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        var application: CxApplication? = null
          private set
    }
}