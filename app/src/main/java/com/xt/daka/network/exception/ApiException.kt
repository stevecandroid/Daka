package com.xt.daka.network.exception

import com.xt.daka.network.youtu.data.model.HttpStatus

/**
 * Created by steve on 17-9-23.
*/

class ApiException(val httpStatus: HttpStatus) : Exception()  {

    override  val message : String by lazy {
        isAccessable()
    }

    private fun isAccessable() : String{
        when(httpStatus.code){
            400 ->  return "请求不合法，包体格式错误"
            404 ->  return "请求失败"
            500 ->  return "服务内部错误"
            503 ->  return "服务器不可用"
        }
        return "!未知错误!"
    }
}