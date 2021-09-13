package com.example.wikisearch.network

import com.example.imageloaderapp.network.ImageApiService


class GenericApiManager : BaseApiManager() {

    init {
        retrofit = initRetrofit(BASE_URL)
    }

    fun getImageApiService(): ImageApiService {
        return retrofit.create(ImageApiService::class.java)
    }


}