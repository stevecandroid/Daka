package com.xt.daka

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.xt.daka.data.model.response.User

/**
 * Created by steve on 17-9-21.
 */
class App : Application() {


    companion object {
        lateinit var app : App
        var user : User? = null
        val testMode = true
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        Utils.init(this)
    }

}