package com.xt.daka.ui.login

import com.xt.daka.base.BasePresenter
import com.xt.daka.base.BaseView

/**
 * Created by steve on 17-9-23.
 */
interface LoginContract{

    interface View : BaseView<Presenter> {

        fun onLoginSuccess()
        fun onLoginFailed(error: LoginError)
        fun onStartLogin()
    }

    interface Presenter: BasePresenter{
        fun login(account : String , password : String)
    }

}