package com.example.imageloaderapp.network

import com.example.imageloader.download.ImageDownloader
import com.example.imageloaderapp.network.response.ImageResponse
import com.example.wikisearch.network.API_IMAGE_LIST

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {
    @GET(API_IMAGE_LIST)
    fun getImageList(): Call<ArrayList<ImageResponse>>
}