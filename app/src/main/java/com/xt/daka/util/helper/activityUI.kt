package com.xt.daka.util.helper

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.view.View



/**
 * Created by steve on 17-9-28.
 */
fun AppCompatActivity.transparentStatusBar() {

    if (Build.VERSION.SDK_INT >= 21) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

    }

}

fun Activity.gameMode() {
    if (Build.VERSION.SDK_INT >= 19) {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}