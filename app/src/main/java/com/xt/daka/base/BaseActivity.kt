package com.xt.daka.base

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import com.data.xt.daka.UtilActivity

@SuppressLint("Registered")
/**
 * Created by steve on 17-9-21.
 */

open class BaseActivity : UtilActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        permissionMgr.request {
            super.onCreate(savedInstanceState)
            permissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET)
        }
    }
}