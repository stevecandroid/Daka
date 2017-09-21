package com.xt.daka.ui.face

import com.xt.daka.ui.face.FaceContract

/**
 * Created by steve on 17-9-21.
 */
class FacePresenter(val view : FaceContract.View) : FaceContract.Presenter {

    init {
        view.setPresenter(this)
    }



    override fun subscribe() {

    }

    override fun unsubscribe() {

    }
}