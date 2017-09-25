package com.xt.daka.ui.face

import com.xt.daka.base.BasePresenter
import com.xt.daka.base.BaseView
import com.xt.daka.network.NetworkCallback
import com.xt.daka.network.youtu.data.model.CompareResult
import com.xt.daka.network.youtu.data.model.HttpStatus

/**
 * Created by steve on 17-9-21.
 */
interface FaceContract{

    interface View : BaseView<Presenter>{
        fun identifySuccess(result:CompareResult)
        fun identifyFailed( msg : Exception )
    }

    interface Presenter : BasePresenter{
        fun identify(path:String,path2 : String)
    }
}