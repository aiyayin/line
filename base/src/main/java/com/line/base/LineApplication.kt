package com.line.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * LineApplication
 * Created by ying.fu on 2018/7/16.
 */
class LineApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}