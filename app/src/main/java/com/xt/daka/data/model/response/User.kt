package com.xt.daka.data.model.response

/**
 * Created by steve on 17-10-16.
 */

data class User(val status : Int, val user : UserDetial) {

    data class UserDetial(val username: String, val name: String, val department: String) {

    }

}