package com.xt.daka.ui.main

import com.xt.daka.base.BaseError

/**
 * Created by steve on 17-9-28.
 */
class LoginError( override var code: Int) : BaseError() {
    companion object {
        val ACCOUNT_INCORRECT = 0;
        val PASSWORD_INCORRECT = 1;
    }
}