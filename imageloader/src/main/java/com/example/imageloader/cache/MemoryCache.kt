package com.example.imageloader.cache

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache:Cache {

    private val cache : LruCache<String, Bitmap>
    val maxMemory = Runtime.getRuntime().maxMemory() /1024
    val defaultCacheSize = (maxMemory/4).toInt()


    init {
        var cacheSize: Int = defaultCacheSize

        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return (value.rowBytes) * (value.height) / 1024
            }
        }
    }

    override fun get(key: String): Bitmap? {
        return  cache.get(key)
    }

    override fun set(key: String, bitmap: Bitmap) {
        cache.put(key,bitmap)
    }

    override fun clear() {
        cache.evictAll()
    }
}