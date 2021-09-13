package com.example.wikisearch.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


abstract class BaseApiManager : KoinComponent {
    private val DEFAULT_CONNECT_TIMEOUT: Long = 20
    private val DEFAULT_WRITE_TIMEOUT: Long = 2
    private val DEFAULT_READ_TIMEOUT: Long = 20
    private val interceptor = HttpLoggingInterceptor()
    private lateinit var okHttpClient: OkHttpClient
    protected lateinit var retrofit: Retrofit

    fun initRetrofit(url: String): Retrofit {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        okHttpClient = builder.build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(url)
            .build()

        return retrofit
    }

    fun cancelAllRequest() {
        okHttpClient.dispatcher().cancelAll()
    }
}