package com.xt.daka.ui.face

import com.xt.daka.base.BasePresenter
import com.xt.daka.base.BaseView
import com.xt.daka.network.NetworkCallback

/**
 * Created by steve on 17-9-21.
 */
interface FaceContract{

    interface View : BaseView<Presenter>,NetworkCallback{

    }

    interface Presenter : BasePresenter{

    }
}