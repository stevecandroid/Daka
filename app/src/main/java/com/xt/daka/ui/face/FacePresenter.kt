package com.xt.daka.ui.face

import android.util.Log
import com.xt.daka.network.exception.ApiException
import com.xt.daka.network.youtu.Youtu
import com.xt.daka.network.youtu.data.model.HttpStatus
import com.xt.daka.util.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by steve on 17-9-21.
 */
class FacePresenter(val view: FaceContract.View) : FaceContract.Presenter {

    val mDisposable : CompositeDisposable = CompositeDisposable()

    override fun identify(path: String, path2: String) {

        val dispose  = Youtu.compare(path, path2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {

            result ->

            val status = HttpStatus(result.code(), result.message())

            if (!status.isAccessable()) {
                view.identifyFailed(ApiException(status))
                unsubscribe()
            }

            result.body()

        }.subscribe(

                { result -> if(result.errorCode==0) view.identifySuccess(result) else view.identifyFailed(Exception("正脸请对准镜头"))},
                { view.identifyFailed( Exception("请检查网络链接")) }
        )

        mDisposable.add(dispose)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mDisposable.clear()

    }
}