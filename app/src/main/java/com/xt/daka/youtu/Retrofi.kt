package com.xt.daka.youtu

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by steve on 17-9-16.
 */
object Retro{


    val Retrofit = youtuRetrofit()

    private fun youtuRetrofit() : Retrofit{
        return  retrofit2.Retrofit.Builder().baseUrl("http://api.youtu.qq.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

}
