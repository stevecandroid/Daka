package com.xt.daka.base

import android.databinding.ViewDataBinding

/**
 * Created by steve on 17-9-21.
 */
interface BaseItemView<T>{
    var viewBinding : ViewDataBinding
    fun setBinding( binding : ViewDataBinding)
    fun bind ( element : T , position: Int)
}
