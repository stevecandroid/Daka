package com.xt.daka.ui.sign

import com.xt.daka.base.BaseError

/**
 * Created by steve on 17-10-16.
 */
class SignError(override var code: Int , var faceStatus : Int) : BaseError() {
    companion object {
        val ERROR_UNKNOW = -1
        val NETWORK_ERROR = 0
        val FLY_MODE_ERROR = 1

        val FACE_LOCAL_PHOTO_ERROR = 1
        val FACE_REMOTE_PHOTO_ERROR = 2
    }
}