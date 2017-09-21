package com.xt.daka

import android.app.Application

/**
 * Created by steve on 17-9-21.
 */
class App : Application() {

    companion object {
        lateinit var app : App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}