package com.data.xt.daka.network.youtu

import com.data.xt.daka.constant.Constant
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST



/**
 * Created by steve on 17-9-16.
 */
interface YoutuApi{

    @POST("youtu/api/facecompare")
    @Headers("Host:api.youtu.qq.com","Content-Type:text/json")
    fun compare(@Header("Authorization") authorization : String , @Body body : CompareBody) : Observable<ResponseBody>

//    @Headers("Host:api.youtu.qq.com","Content-Type:text/json")
//    @POST("youtu/api/newperson")
//    fun newPerson()
//
//    @Headers("Host:api.youtu.qq.com","Content-Type:text/json")
//    @POST("youtu/api/getgroupids")
//    fun getGroupids(@Header("Authorization") authorization : String ,@Body body : GroupidsBody) : Call<ResponseBody>
//
//    @Headers("Host:api.youtu.qq.com","Content-Type:text/json")
//    @POST("/youtu/api/getpersonids")
//    fun getPersonids(@Header("Authorization") authorization : String ,@Body body : PersonidsBody)

}

abstract class BaseBody( val app_id : String = Constant.appId)

//class GroupidsBody : BaseBody()
data class CompareBody( val imageA : String, val imageB : String ) : BaseBody()
//data class PersonidsBody( val group_id : String) : BaseBody()
