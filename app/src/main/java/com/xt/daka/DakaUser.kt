package com.xt.daka

import android.graphics.Bitmap
import com.xt.daka.data.model.request.ParamsFaceAcquire
import com.xt.daka.data.model.request.ParamsLogin
import com.xt.daka.data.model.response.User
import com.xt.daka.data.source.remote.api.FaceApi
import com.xt.daka.network.RetrofitClient
import com.xt.daka.network.youtu.Youtu
import com.xt.daka.network.youtu.data.model.CompareResult
import com.xt.daka.ui.login.LoginException
import com.xt.daka.ui.sign.SignException
import io.reactivex.Observable

/**
 * Created by steve on 17-10-20.
 */
object DakaUser {

    var user: User? = null

    fun login(account: String, password: String) =
            RetrofitClient.faceClient.create(FaceApi::class.java)
                    .login(ParamsLogin(account, password))
                    .map { map ->
                        map.body()
                    }
                    .doOnNext { user ->
                        if(user.status != 3){
                            throw LoginException(user.status)
                        }else{
                            DakaUser.user = user
                        }
                    }

    fun signin(bm: Bitmap) =
            RetrofitClient.faceClient.create(FaceApi::class.java)
                    .getface(ParamsFaceAcquire(user!!.user.name))
                    .map { body ->
                        body.body()
                    }
                    .flatMap { mapper ->
                        if (mapper.status == 1)
                            Youtu.compareBase64(bm, mapper.imageStringData).doOnNext {
                                result ->
                                if(result.errorCode != 0 ){
                                    throw SignException(result.flag, result.errorMsg)
                                }
                            }
                        else {
                            Observable.create<CompareResult> { emitter ->
                                emitter.onError(SignException(-1,"无法找到人脸,请联系管理员"))
                            }
                        }

                    }


}