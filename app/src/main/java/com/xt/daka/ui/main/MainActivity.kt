package com.xt.daka.ui.main

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.widget.Toast
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.xt.daka.App
import com.xt.daka.R
import com.xt.daka.base.BaseActivity
import com.baidu.location.LocationClientOption.LocationMode
import com.baidu.location.LocationClientOption
import org.jetbrains.anko.toast



class MainActivity : BaseActivity(){

    private val mLocationClient : LocationClient by lazy {
        val client = LocationClient(App.app.applicationContext)
        initLocation(client)
        client

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAnimation()

        mLocationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                toast("经度${location!!.longitude} 纬度${location.latitude} 精度${location.radius}")

            }

            override fun onLocDiagnosticMessage(locType: Int, diagnosticType: Int, diagnosticMessage: String?) {
                super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage)

                when(diagnosticType){
                    LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI,
                    LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET ->
                        toast("网络异常,请见检查网络！")

                    LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE ->
                            toast("请关闭飞行模式后再重试定位")
                }
            }

            override fun onConnectHotSpotMessage(p0: String?, p1: Int) {
                super.onConnectHotSpotMessage(p0, p1)
            }
        })
        mLocationClient.start()


    }

    private fun initLocation( client : LocationClient) {

        val option = LocationClientOption()
        option.locationMode = LocationMode.Hight_Accuracy
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll")
        //可选，默认gcj02，设置返回的定位结果坐标系

        val span = 1000
        option.setScanSpan(span)
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true)
        //可选，设置是否需要地址信息，默认不需要

        option.isOpenGps = true
        //可选，默认false,设置是否使用gps

        option.isLocationNotify = true
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false)
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死


        client.locOption = option
    }

    fun initAnimation(){
        window.enterTransition = Slide().setDuration(400)
        window.exitTransition = Slide().setDuration(400)
    }

}
