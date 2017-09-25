package com.xt.daka.util

import android.widget.Toast
import com.xt.daka.App
import org.jetbrains.anko.runOnUiThread

/**
 * Created by steve on 17-9-22.
 */
fun Any.toast(msg : Any){
    App.app.runOnUiThread { Toast.makeText(this,msg.toString(),Toast.LENGTH_SHORT).show() }
}