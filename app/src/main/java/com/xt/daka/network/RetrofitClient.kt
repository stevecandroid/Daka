package com.xt.daka.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by steve on 17-9-16.
 */
object RetrofitClient{


    val youtuClient : Retrofit by lazy {
        youtuRetrofit()
    }

    val faceClient : Retrofit by lazy {
        faceClient()
    }

    private fun youtuRetrofit() : Retrofit{
        return  retrofit2.Retrofit.Builder().baseUrl("http://api.youtu.qq.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun faceClient() : Retrofit {
        return  retrofit2.Retrofit.Builder().baseUrl("http://10.21.71.107:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }




}
