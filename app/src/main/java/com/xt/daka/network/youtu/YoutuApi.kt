package com.xt.daka.network.youtu

import com.xt.daka.network.youtu.data.model.CompareBody
import com.xt.daka.network.youtu.data.model.CompareResult
import io.reactivex.Observable
import retrofit2.Response

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
    fun compare(@Header("Authorization") authorization : String , @Body body : CompareBody) : Observable<CompareResult>

}


//data class PersonidsBody( val group_id : String) : BaseBody()
