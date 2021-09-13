package com.example.imageloader

import android.content.Context
import android.net.Uri
import com.example.imageloader.cache.Cache
import com.example.imageloader.cache.MemoryCache

class ImageLoader private constructor(context: Context){



    fun load(path:String) :RequestBuilder{

        require(path.trim { it <= ' ' }.isNotEmpty()) { "Path must not be empty." }

     return RequestBuilder(this,Uri.parse(path))
    }
    fun submit(imageRequestData: RequestBuilder.ImageRequestData){
        mImageLoaderService.performSubmit(imageRequestData)
    }
    fun cancle(){
        mImageLoaderService.onCancel()
    }

    companion object {
        private lateinit var  imageLoaderInsatnce: ImageLoader
        private lateinit var mCache: Cache
        private lateinit var mImageLoaderService: ImageLoaderService
        @Synchronized
        fun with(context: Context): ImageLoader {
            imageLoaderInsatnce=if(::imageLoaderInsatnce.isInitialized) imageLoaderInsatnce
            else build(context)
             return imageLoaderInsatnce

        }

        fun build(context: Context) : ImageLoader{
            mCache= MemoryCache()
            mImageLoaderService=ImageLoaderService(mCache,context)
            return ImageLoader(context)
        }
    }
}