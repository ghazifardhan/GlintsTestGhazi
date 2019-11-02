package com.ghazifardhan.glintstestghazi.api

import com.ghazifardhan.glintstestghazi.BuildConfig
import com.ghazifardhan.glintstestghazi.api.UnauthorizedInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InitLibrary {

    fun getAppStatus(): String {
        return when (BuildConfig.DEBUG) {
            true -> "http://glints.test"
            false -> "http://ikat.ghazifadil.online"
        }
    }

    private fun setInit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = (HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(UnauthorizedInterceptor())
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(getAppStatus())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): ApiServices {
        return setInit().create(ApiServices::class.java)
    }

}