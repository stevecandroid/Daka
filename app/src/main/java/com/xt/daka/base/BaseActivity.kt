package com.xt.daka.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.data.xt.daka.UtilActivity

@SuppressLint("Registered")
/**
 * Created by steve on 17-9-21.
 */

open class BaseActivity : UtilActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        permissionMgr.request {
            permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
        }
        window.statusBarColor = Color.GRAY
    }
}