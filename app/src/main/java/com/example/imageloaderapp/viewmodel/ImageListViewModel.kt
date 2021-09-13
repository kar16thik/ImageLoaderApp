package com.example.imageloaderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageloaderapp.network.response.ImageResponse
import com.example.imageloaderapp.repository.ImageListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImageListViewModel :  ViewModel(), KoinComponent {

    private var mImageListLiveData: MutableLiveData<ArrayList<ImageResponse>> = MutableLiveData()
    private var mErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private val mImageListRepository: ImageListRepository by inject()

    fun initImageList(){
        getImageList()
    }

   private fun getImageList(){
        mImageListRepository.getImageList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { },
                onNext = { it ->
                    if(it!=null) {
                        mImageListLiveData.value=it
                    }
                },
                onError = {
                    mErrorLiveData.value=it.message
                }
            )
    }

    fun getImageListLiveData(): LiveData<ArrayList<ImageResponse>> = mImageListLiveData
    fun getErrorLiveData(): LiveData<String> = mErrorLiveData
}