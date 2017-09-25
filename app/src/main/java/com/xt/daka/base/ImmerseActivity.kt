package com.xt.daka.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View

/**
 * Created by steve on 17-9-23.
 */
open class ImmerseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {

            val option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or  View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            window.statusBarColor = Color.TRANSPARENT
            window.decorView.setSystemUiVisibility(option)
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT


        }
    }
}