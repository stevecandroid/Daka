package com.xt.daka.network.youtu.data.model

import com.data.xt.daka.constant.Constant
import com.google.gson.annotations.SerializedName

/**
 * Created by steve on 17-9-22.
 */
abstract class BaseBody( val app_id : String = Constant.appId)

//class GroupidsBody : BaseBody()
data class CompareBody( val imageA : String,
                        val imageB : String ) : BaseBody()

data class CompareResult(val similarity : Float ,
                         @SerializedName("fail_flag") val flag : Int ,
                         @SerializedName("errorcode") val errorCode : Int,
                         @SerializedName("errormsg") val errorMsg : String)

data class HttpStatus(val code : Int, val message : String){

    fun isAccessable() :Boolean{
        return code < 400
    }
}