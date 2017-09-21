package com.data.xt.daka.util.sensor

import android.content.Context
import org.jetbrains.anko.sensorManager

/**
 * Created by steve on 17-9-18.
 */
abstract class Tracker(ctx: Context) {
    val manager = ctx.sensorManager
}