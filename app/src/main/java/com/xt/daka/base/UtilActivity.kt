package com.data.xt.daka

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity

import com.xt.daka.util.pic.AlbumPicker
import com.xt.daka.util.pic.PhotoTaker
import com.xt.java3.util.pic.PermissionsWrapper


/**
 * Created by steve on 17-8-31.
 */
@SuppressLint("Registered")
open class UtilActivity : AppCompatActivity() {


    var permissionMgr = PermissionsWrapper.PermissionMgr.with(this)
    var albumPicker = AlbumPicker.with(this)
    var photoTaker = PhotoTaker.with(this)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionMgr.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        albumPicker.onActivityResult(requestCode,resultCode,data)
        photoTaker.onActivityResult(requestCode,resultCode,data)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}