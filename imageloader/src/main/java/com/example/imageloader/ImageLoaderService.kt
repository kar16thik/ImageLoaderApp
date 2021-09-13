package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.imageloader.cache.Cache
import com.example.imageloader.download.ImageDownloader
import kotlinx.coroutines.*
import java.io.File
import android.app.Activity
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.LinkedHashMap


class ImageLoaderService  (cache: Cache, context: Context) : LifecycleObserver {

    var mCache: Cache = cache
    var mCacheDir: File
     var downloadableMap:LinkedHashMap<String, Job>
    var failedDownloadableMap:LinkedHashMap<String, RequestBuilder.ImageRequestData>
   var mImageDownloader:ImageDownloader

    init {
        mCacheDir=createDefaultCacheDir(context)
        failedDownloadableMap=LinkedHashMap<String, RequestBuilder.ImageRequestData>()
        downloadableMap= LinkedHashMap<String, Job>();
        mImageDownloader=ImageDownloader(mCacheDir)
    }



    fun performSubmit(imageRequestData: RequestBuilder.ImageRequestData){
        val imageBitmap = mCache.get(imageRequestData.uri.toString())
        imageBitmap?.let {
            showImage(imageRequestData.targetImageView,it)
            Log.d("IMAGE_LOAD","MEMORTY_CACHE")
        }?: run {
            downloadImage(imageRequestData)
        }
    }

    fun downloadImage(imageRequestData: RequestBuilder.ImageRequestData){
      val job=  CoroutineScope(Dispatchers.IO).launch {
          val result =mImageDownloader.download(imageRequestData.uri)
          withContext(Dispatchers.Main) {

                if(result.imageBitMap!=null) {

                    onSuccess(imageRequestData.targetImageView, result)
                }else{
                    onFailure(imageRequestData)
                }
          }
        }
        downloadableMap.put(imageRequestData.uri.toString(),job)
        }


    fun onSuccess(imageview : ImageView?,imageResponse: ImageDownloader.ImageResponse){
        imageResponse.imageBitMap?.let {
            mCache.set(imageResponse.imageUri.toString(),imageResponse.imageBitMap)
            showImage(imageview,imageResponse.imageBitMap)
        }

        downloadableMap.remove(imageResponse.imageUri.toString())
        if(imageResponse.isFromCatch){
            Log.d("IMAGE_LOAD","DISC_CACHE")
        }else{
            Log.d("IMAGE_LOAD","NETWORK")
        }

    }

    fun onFailure(requestData:RequestBuilder.ImageRequestData){
        if(requestData.errorResId>0) {
            requestData.targetImageView?.setImageResource(requestData.errorResId);
        }
        failedDownloadableMap.put(requestData.uri.toString(),requestData)
    }
    fun onCancel(){
        for ((uri,job) in downloadableMap){
            if(!job.isCancelled){
                job.cancel()
            }
        }
        downloadableMap.clear()

    }

    fun showImage(imageview : ImageView?,imageBitmap: Bitmap){
        imageview?.setImageBitmap(imageBitmap )
    }

    private fun createDefaultCacheDir(context: Context): File {
        val cache =
            File(context.applicationContext.cacheDir,IMAGE_CACHE)
        if (!cache.exists()) {
            cache.mkdirs()
        }
        return cache
    }
}