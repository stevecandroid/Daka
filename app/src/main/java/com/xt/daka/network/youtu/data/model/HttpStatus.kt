package com.xt.daka.network.youtu.data.model

/**
 * Created by steve on 17-10-16.
 */
data class HttpStatus(val code : Int, val message : String){

    fun isAccessable() :Boolean{
        return code < 400
    }
}