package com.communityx.application

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {

        var application: MyApplication? = null
            private set
    }
}
