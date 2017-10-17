package com.xt.daka.network.youtu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.data.xt.daka.constant.Constant
import com.data.xt.daka.util.pic.bitmap.BitmapUtil
import com.xt.daka.network.RetrofitClient
import com.xt.daka.network.youtu.data.model.CompareBody
import com.xt.daka.network.youtu.data.model.CompareResult
import io.reactivex.Observable
import retrofit2.Response


class Youtu {

    companion object {
        val DELAY = 24 * 60 * 60

        private fun createYoutuSign(): String {
            val mySign = StringBuffer("")
            appSign(Constant.appId, Constant.secretID, Constant.secretKey,
                    System.currentTimeMillis() / 1000 + DELAY ,Constant.userId, mySign)
            return mySign.toString()
        }

        fun compare(path1: String, path2: String) : Observable<Response<CompareResult>> {

            val bitmap1 = BitmapUtil.getOptimalBitmap(path1)
            val bitmap2 = BitmapUtil.getOptimalBitmap(path2)

            val byteBitmap1 = BitmapUtil.bitmapToBase64(bitmap1)
            val byteBitmap2 = BitmapUtil.bitmapToBase64(bitmap2)

            val body = CompareBody(byteBitmap1, byteBitmap2)
            return RetrofitClient.youtuClient.create(YoutuApi::class.java)
                    .compare(createYoutuSign(), body)
        }

        fun compareBase64( path:String, base64 : String) : Observable<Response<CompareResult>>{

            val bitmap = BitmapUtil.getOptimalBitmap(path)
            val base64Bitmap = BitmapUtil.bitmapToBase64(bitmap)

            return RetrofitClient.youtuClient.create(YoutuApi::class.java)
                    .compare(createYoutuSign(),CompareBody(base64Bitmap,base64))
        }

        fun compareBase64( bm : Bitmap , base64 : String): Observable<Response<CompareResult>>{
            val base64Bitmap = BitmapUtil.bitmapToBase64(bm)
            return RetrofitClient.youtuClient.create(YoutuApi::class.java)
                    .compare(createYoutuSign(),CompareBody(base64Bitmap,base64))
        }

//        fun compareWithoutCompress(path1:String,path2:String) : Observable<Response<CompareResult>> {
//
//            val bitmap1 = BitmapFactory.decodeFile(path1)
//            val bitmap2 = BitmapFactory.decodeFile(path2)
//
//            val byteBitmap1 = BitmapUtil.bitmapToBase64(bitmap1)
//            val byteBitmap2 = BitmapUtil.bitmapToBase64(bitmap2)
//
//            val body = CompareBody(byteBitmap1, byteBitmap2)
//            return RetrofitClient.youtuClient.create(YoutuApi::class.java)
//                    .compare(createYoutuSign(), body)
//
//        }
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