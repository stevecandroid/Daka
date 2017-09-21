package com.xt.daka.util.helper

import android.util.Log
import android.view.ViewTreeObserver

/**
 * Created by steve on 17-9-20.
 */
class GlobalLayoutHelper : ViewTreeObserver.OnGlobalLayoutListener{
    
    override fun onGlobalLayout() {
        Log.e("GlobalLayoutHelper","Im layouting ")
    }

}