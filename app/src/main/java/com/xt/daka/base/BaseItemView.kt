package com.xt.daka.base

/**
 * Created by steve on 17-9-21.
 */
interface BaseItemView<T>{
    fun bind ( element : T , position: Int)
}
