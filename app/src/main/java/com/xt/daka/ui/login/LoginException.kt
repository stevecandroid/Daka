package com.xt.daka.ui.login

/**
 * Created by steve on 17-10-20.
 */
class LoginException(val status : Int , message : String ="") : RuntimeException( message) {

}