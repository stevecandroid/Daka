package com.xt.daka.ui.sign

import android.graphics.Bitmap
import com.xt.daka.base.BaseError
import com.xt.daka.base.BasePresenter
import com.xt.daka.base.BaseView

/**
 * Created by steve on 17-10-15.
 */
interface SignContract {

    interface View : BaseView<Presenter>{
        fun onLocatedError()
        fun onLocated(location : String)
        fun onSignStart()
        fun onSignSuccess( similarity : Float)
        fun onSignError( error : Throwable)
    }

    interface Presenter : BasePresenter{
        fun startLocation()
        fun stopLocation()
        fun faceCompare( bm : Bitmap )
    }

}