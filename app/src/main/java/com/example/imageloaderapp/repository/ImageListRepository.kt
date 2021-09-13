package com.example.imageloaderapp.repository

import com.example.imageloaderapp.network.response.ImageResponse
import com.example.wikisearch.network.GenericApiManager
import io.reactivex.subjects.PublishSubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageListRepository : KoinComponent {
    private val apiMananger: GenericApiManager by inject()

    fun getImageList(): PublishSubject<ArrayList<ImageResponse>> {
        val responseSubject = PublishSubject.create<ArrayList<ImageResponse>>()
        val dashboardApiService = apiMananger.getImageApiService()

        val call: Call<ArrayList<ImageResponse>> = dashboardApiService.getImageList()
        call.enqueue(object : Callback<ArrayList<ImageResponse>> {
            override fun onResponse(call: Call<ArrayList<ImageResponse>>, response: Response<ArrayList<ImageResponse>>) {

                var imageList = response.body()
                imageList?.let {
                    responseSubject.onNext(imageList)
                }
                responseSubject.onComplete()


            }
            override fun onFailure(call: Call<ArrayList<ImageResponse>>, t: Throwable) {
                responseSubject.onError(Throwable((t.message)))
                responseSubject.onComplete()

            }
        })

        return responseSubject
    }
}