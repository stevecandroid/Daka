package com.xt.daka.data.source.remote.api

import com.xt.daka.data.model.request.ParamsFaceAcquire
import com.xt.daka.data.model.request.ParamsLogin
import com.xt.daka.data.model.response.Face
import com.xt.daka.data.model.response.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by steve on 17-10-16.
 */
interface FaceApi {

    @Headers( "Accept:application/json","Content-Type:application/json")
    @POST("FaceRecognize/login/user")
    fun login(@Body params : ParamsLogin) : Observable<Response<User>>


    @Headers("Accept:application/json; charset=utf-8" , "Content-Type:application/json; charset=utf-8")
    @POST("FaceRecognize/duty/picture/match")
    fun getface(@Body params : ParamsFaceAcquire) : Observable<Response<Face>>
}