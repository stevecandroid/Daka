package com.xt.daka.util.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.data.xt.daka.util.sensor.Tracker

/**
 * Created by steve on 17-9-18.
 */


class TrackerWrapper {

    private var _onStable : (() ->Unit)? = null
    private var _onNotStable : (()->Unit)? = null

    fun onStable( f : ()->Unit){
        _onStable = f
    }

    fun onNotStable( f: ()->Unit){
        _onNotStable = f
    }



    class StableTracker(ctx: Context) : Tracker(ctx) {

        val sensitivity = 1.4f
        val trackerWrapper = TrackerWrapper()


        private var gravity = arrayOf(0f, 0f, 0f)
        val sensorListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

            override fun onSensorChanged(event: SensorEvent?) {
                when (event!!.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER ->
                        run {
                            if ((event.values[0] - gravity[0]).between(-sensitivity, sensitivity) &&
                                    (event.values[1] - gravity[1]).between(-sensitivity, sensitivity) &&
                                    (event.values[2] - gravity[2]).between(-sensitivity, sensitivity)) {

                                if(trackerWrapper._onStable != null) trackerWrapper._onStable!!.invoke()

                            } else {
                                if(trackerWrapper._onNotStable != null) trackerWrapper._onNotStable!!.invoke()
                            }
                        }


                    Sensor.TYPE_GRAVITY ->
                        run {
                            gravity[0] = event.values[0]
                            gravity[1] = event.values[1]
                            gravity[2] = event.values[2]
                        }


                }
            }
        }

        fun addStateChangedListnere( f : TrackerWrapper.()->Unit ){
            trackerWrapper.f()
        }

        fun register() {
            manager.registerListener(sensorListener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI)
            manager.registerListener(sensorListener, manager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_UI)
        }

        fun unregister() {
            manager.unregisterListener(sensorListener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))
            manager.unregisterListener(sensorListener, manager.getDefaultSensor(Sensor.TYPE_GRAVITY))
        }

        fun Float.between(from: Float, to: Float): Boolean {
            if (this > from && this < to) return true
            return false
        }


    }
}