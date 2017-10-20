package com.xt.daka.ui.sign

import android.graphics.Bitmap
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.xt.daka.App
import com.xt.daka.DakaUser
import com.xt.daka.data.model.request.ParamsFaceAcquire
import com.xt.daka.data.source.remote.api.FaceApi
import com.xt.daka.network.RetrofitClient
import com.xt.daka.network.youtu.Youtu
import com.xt.daka.network.youtu.data.model.CompareResult
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by steve on 17-10-15.
 */
class SignPresenter(val view: SignContract.View) : SignContract.Presenter {


    private val mLocationClient: LocationClient by lazy {
        val client = LocationClient(App.app.applicationContext)
        initLocation(client)
        client
    }

    override fun subscribe() {
        mLocationClient.registerLocationListener(object : BDAbstractLocationListener() {

            override fun onReceiveLocation(location: BDLocation?) {
//                toast("经度${location!!.city} 纬度${location.address.address}")
//                location.poiList.forEach { l ->
//                    Log.e("SignActivity", "${l.name} and ${l.id}  and  ${l.rank}")
//                    location.indoorLocationSurpport = 1
//                }
                if (location != null) {
                    if (location.poiList.size > 0) {
                        view.onLocated(location.poiList[0].name)
                        stopLocation()
                    }
                }
            }


            override fun onLocDiagnosticMessage(locType: Int, diagnosticType: Int, diagnosticMessage: String?) {
                super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage)

//                when (diagnosticType) {
//                    LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI,
//                    LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET ->
//                        view.onLocatedError()
//
//                    LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE ->
//
//                }]
                view.onLocatedError()
            }

            override fun onConnectHotSpotMessage(p0: String?, p1: Int) {
                super.onConnectHotSpotMessage(p0, p1)
            }
        })
        mLocationClient.start()
    }

    override fun stopLocation() {
        mLocationClient.stop()
    }

    override fun startLocation() {
        if (!mLocationClient.isStarted)
            mLocationClient.start()
    }

    override fun faceCompare(bm: Bitmap) {

//        if (App.testMode) {
//            val bm2 = BitmapUtil.getOptimalBitmap(Environment.getExternalStorageDirectory().path + "/abc.jpg")
//            val base64 = BitmapUtil.bitmapToBase64(bm2)
//            Youtu.compareBase64(bm, base64).subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(object : Observer<CompareResult> {
//
//                        override fun onError(e: Throwable?) {
//                            view.onSignError(LocatedError(LocatedError.ERROR_UNKNOW,LocatedError.FACE_NETWORK_ERROR,"网络出错"))
//                        }
//
//                        override fun onComplete() {
//
//                        }
//
//                        override fun onNext(result: CompareResult?) {
//
//                            if (result != null) {
//
//                                Log.e("SignPresenter",result.errorCode.toString())
//
//                                when(result.errorCode){
//                                    0 -> view.onSignSuccess(result.similarity)
//
//                                    LocatedError.FACE_REMOTE_POHOTO_NOT_EXIST ->
//                                        view.onSignError(LocatedError(LocatedError.FACE_REMOTE_POHOTO_NOT_EXIST,result.flag,result.errorMsg))
//
//                                    else ->  view.onSignError(LocatedError(LocatedError.ERROR_UNKNOW,result.flag,result.errorMsg))
//                                }
//                            } else {
//                                view.onSignError(LocatedError(LocatedError.ERROR_UNKNOW,-500,"返回体空"))
//                            }
//                        }
//
//                        override fun onSubscribe(d: Disposable?) {
//                            view.onSignStart()
//                        }
//                    })
//
//        }

         DakaUser.signin(bm).subscribe(object : Observer<CompareResult> {

                override fun onError(e: Throwable?) {
                    view.onSignError(e!!)
                }

                override fun onComplete() {
                }

                override fun onNext(result: CompareResult?) {
                    view.onSignSuccess(result!!.similarity)
                }

                override fun onSubscribe(d: Disposable?) {
                    view.onSignStart()
                }

            })


    }

    override fun unsubscribe() {
        stopLocation()
    }

    private fun initLocation(client: LocationClient) {

        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
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

}