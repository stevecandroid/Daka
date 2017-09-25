package com.xt.daka.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by steve on 17-9-16.
 */
object RetrofitClient{


    val youtuClient = youtuRetrofit()

    private fun youtuRetrofit() : Retrofit{
        return  retrofit2.Retrofit.Builder().baseUrl("http://api.youtu.qq.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}
