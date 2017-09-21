package com.data.xt.daka.util.youtu

import android.graphics.BitmapFactory
import android.util.Log
import com.blankj.utilcode.util.ImageUtils
import com.data.xt.daka.constant.Constant
import com.data.xt.daka.network.youtu.CompareBody
import com.xt.daka.youtu.Retro
import com.data.xt.daka.network.youtu.YoutuApi
import com.xt.daka.youtu.appSign
import com.data.xt.daka.util.pic.bitmap.BitmapUtil
import okhttp3.ResponseBody


object Youtu {
    private fun createYoutuSign(): String {
        val mySign = StringBuffer("")
        appSign(Constant.appId, Constant.secretID, Constant.secretKey,
                System.currentTimeMillis() / 1000 + 24 * 60 * 60, Constant.userId, mySign)
        return mySign.toString()
    }

    fun compare(path1: String?, path2: String?) {
        val byteBitmap1 = BitmapUtil.bitmapToBase64(ImageUtils.compressByScale(BitmapFactory.decodeFile(path1),500,500))
        val byteBitmap2 = BitmapUtil.bitmapToBase64(ImageUtils.compressByScale(BitmapFactory.decodeFile(path2),500,500))
        val body = CompareBody(byteBitmap1, byteBitmap2)
        Retro.Retrofit.create(YoutuApi::class.java).compare(createYoutuSign(), body)

    }

//    fun compare(path1: ByteArray?, path2: ByteArray?) {
//        val byteBitmap1 = BitmapUtil.bitmapToBase64(ImageUtils.compressByScale(BitmapFactory.decodeByteArray(path2,0,0),500,500))
//        val byteBitmap2 = BitmapUtil.bitmapToBase64(ImageUtils.compressByScale(BitmapFactory.decodeByteArray(path1,0,0),500,500))
//        val body = CompareBody(byteBitmap1, byteBitmap2)
//        Retro.Retrofit.create(YoutuApi::class.java).compare(createYoutuSign(), body).subscribe(
//                { t: ResponseBody? -> Log.e("Test", t!!.string()) },
//                {error-> error.printStackTrace()},
//                {Log.e("Youtu","Complete")},
//                {}
//        )
//    }

//    fun compare(path1: ByteArray?, path2: String?) {
//        val byteBitmap1 = BitmapUtil.bitmapToBase64(ImageUtils.compressByScale(BitmapFactory.decodeByteArray(path1,0,0),500,500))
//        val byteBitmap2 = BitmapUtil.bitmapToBase64(ImageUtils.compressByScale(BitmapFactory.decodeFile(path2),500,500))
//        val body = CompareBody(byteBitmap1, byteBitmap2)
//        Retro.Retrofit.create(YoutuApi::class.java).compare(createYoutuSign(), body).subscribe(
//                { t: ResponseBody? -> Log.e("Test", t!!.string()) },
//                {error-> error.printStackTrace()},
//                {Log.e("Youtu","Complete")}
//        )
//    }
}