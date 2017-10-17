package com.xt.daka.network.youtu.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by steve on 17-10-16.
 */
data class CompareResult(val similarity : Float,
                         @SerializedName("fail_flag") val flag : Int,
                         @SerializedName("errorcode") val errorCode : Int,
                         @SerializedName("errormsg") val errorMsg : String)