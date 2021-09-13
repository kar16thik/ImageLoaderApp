package com.example.imageloader

import android.net.Uri
import android.widget.ImageView

class RequestBuilder(imageLoader: ImageLoader, uri: Uri) {

    private lateinit var mImageRequestData:ImageRequestData
    private lateinit var mImageLoader: ImageLoader

    init {
        mImageRequestData=  ImageRequestData(uri)
        mImageLoader=imageLoader
    }

    fun into(imageView: ImageView){

        requireNotNull(imageView) { "Target must not be null." }
        mImageRequestData.targetImageView=imageView
        mImageLoader.submit(mImageRequestData)
    }


    fun error(errorResId: Int): RequestBuilder {
        require(errorResId != 0) { "Error image resource invalid." }
        mImageRequestData.errorResId=errorResId
        return this
    }


    data class ImageRequestData(val uri: Uri, var errorResId:Int=0,var targetImageView:ImageView?=null)

}