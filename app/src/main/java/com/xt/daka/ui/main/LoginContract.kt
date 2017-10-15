package com.xt.daka.ui.main

import com.xt.daka.base.BasePresenter
import com.xt.daka.base.BaseView
import com.xt.daka.network.NetworkCallback

/**
 * Created by steve on 17-9-23.
 */
interface LoginContract{

    interface View : BaseView<Presenter> {
        fun onLoginSuccess();
        fun onLoginFailed(error: LoginError);
    }

    interface Presenter: BasePresenter{
        fun login(account : String , password : String)
    }

}