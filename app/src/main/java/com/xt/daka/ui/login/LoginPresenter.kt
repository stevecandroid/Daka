package com.xt.daka.ui.login

import com.xt.daka.App
import com.xt.daka.DakaUser
import com.xt.daka.data.model.request.ParamsLogin
import com.xt.daka.data.model.response.User
import com.xt.daka.data.source.remote.api.FaceApi
import com.xt.daka.network.RetrofitClient
import com.xt.daka.util.helper.toast
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by steve on 17-9-23.
 */
class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {


    override fun login(account: String, password: String) {

        if(App.testMode){
            view.onLoginSuccess()
            return
        }

       DakaUser.login(account, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Observer<User> {
                            override fun onComplete() {
                            }
                            override fun onSubscribe(d: Disposable?) {
                                view.onStartLogin()
                            }

                            override fun onError(e: Throwable) {
                                view.onLoginFailed(e)
                            }

                            override fun onNext(user: User?) {
                                view.onLoginSuccess()
                            }

                        }
                )

    }


    override fun subscribe() {

    }

    override fun unsubscribe() {

    }


}