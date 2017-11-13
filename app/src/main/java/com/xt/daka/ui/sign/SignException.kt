package com.xt.daka.ui.sign

/**
 * Created by steve on 17-10-20.
 */
class SignException( val status : Int , message : String = "" ) : RuntimeException(message){
    companion object {
        val LOCAL_PHOTO_ERROR = 1
        val REMOTE_PHOTO_ERROR = 2
        val SIMILARITY_TOO_SMALL = 3
    }

}