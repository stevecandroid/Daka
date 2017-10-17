package com.xt.daka.ui.login

import com.xt.daka.App
import com.xt.daka.data.model.request.ParamsLogin
import com.xt.daka.data.source.remote.api.FaceApi
import com.xt.daka.network.RetrofitClient
import com.xt.daka.util.helper.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by steve on 17-9-23.
 */
class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    var mSubscription: Disposable? = null

    override fun login(account: String, password: String) {

        if(App.testMode){
            view.onLoginSuccess()
            return
        }

        mSubscription = RetrofitClient.faceClient.create(FaceApi::class.java).login(ParamsLogin(account, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { next ->
                    next.code(); toast("LoginPresenter  " + next.code().toString())
                }
                .map { next -> next.body() }
                .subscribe(
                        { user ->
                            if (user != null) {
                                with(user.status) {
                                    if (equals("3000")) {
                                        view.onLoginSuccess()
                                        App.user = user
                                    } else if (equals("2000")) {
                                        view.onLoginFailed(LoginError(LoginError.PASSWORD_INCORRECT))
                                    } else {
                                        view.onLoginFailed(LoginError((LoginError.ACCOUNT_INCORRECT)))
                                    }
                                }
                            } else {
                                toast("User is NULL")
                            }
                        },

                        { error -> view.onLoginFailed(LoginError(LoginError.CONNECT_TIMEOUT)) },

                        {},

                        { view.onStartLogin() }

                )

    }


    override fun subscribe() {

    }

    override fun unsubscribe() {
        if (mSubscription != null) mSubscription!!.dispose()
    }


}