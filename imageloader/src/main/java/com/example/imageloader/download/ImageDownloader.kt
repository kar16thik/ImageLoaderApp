package com.example.imageloader.download

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.net.http.HttpResponseCache
import android.os.StatFs
import com.example.imageloader.*
import java.io.File

import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloader(cacheDir: File) {


var responseCache:HttpResponseCache?=null
     var mCacheDir: File

    init{
        mCacheDir= cacheDir
    }



    fun download( uri:Uri):ImageResponse{

        installCache()
        val connection = openConnection(uri)
        connection.useCaches=true
        connection.addRequestProperty("Cache-Control", "max-stale=604800");
      val  bitmap = BitmapFactory.decodeStream(connection.inputStream)
        val fromCache = parseResponseSourceHeader(connection.getHeaderField(RESPONSE_SOURCE))
        connection.disconnect()
       return ImageResponse(bitmap,fromCache,uri)
    }



    private fun openConnection(path: Uri): HttpURLConnection {
        val connection = URL(path.toString()).openConnection() as HttpURLConnection
        connection.connectTimeout = DEFAULT_CONNECT_TIMEOUT_MILLIS
        connection.readTimeout = DEFAULT_READ_TIMEOUT_MILLIS
        return connection
    }

    private fun installCache(){
        if (responseCache == null) {
            responseCache =install(mCacheDir)
        }
    }


    private fun install(cacheDir: File):HttpResponseCache {

        var cache = HttpResponseCache.getInstalled()
        if (cache == null) {
            val maxSize: Long = calculateDiskCacheSize(cacheDir)
            cache = HttpResponseCache.install(cacheDir, maxSize)
        }
        return cache
    }

    fun close(cache: Any) {
        try {
            (cache as HttpResponseCache).close()
        } catch (ignored: IOException) {
        }
    }

    fun calculateDiskCacheSize(dir: File): Long {
        var size: Long = MIN_DISK_CACHE_SIZE.toLong()
        try {
            val statFs = StatFs(dir.absolutePath)
            val available = statFs.blockCount.toLong() * statFs.blockSize
            // Target 2% of the total space.
            size = available / 50
        } catch (ignored: IllegalArgumentException) {
        }

        // Bound inside min/max size for disk cache.
        return Math.max(
            Math.min(size, MAX_DISK_CACHE_SIZE.toLong()),
           MIN_DISK_CACHE_SIZE.toLong()
        )
    }

    fun parseResponseSourceHeader(header: String?): Boolean {
        if (header == null) {
            return false
        }
        val parts = header.split(" ".toRegex(), 2).toTypedArray()
        if ("CACHE" == parts[0]) {
            return true
        }
        return if (parts.size == 1) {
            false
        } else try {
            "CONDITIONAL_CACHE" == parts[0] && parts[1].toInt() == 304
        } catch (e: NumberFormatException) {
            false
        }
    }

    data class ImageResponse(val imageBitMap:Bitmap?,val isFromCatch:Boolean,val imageUri:Uri)
}