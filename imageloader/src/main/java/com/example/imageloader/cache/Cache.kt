package com.example.imageloader.cache

import android.graphics.Bitmap

interface Cache {

    fun get(key :String): Bitmap?
    fun set( key:String,  bitmap: Bitmap)
    fun clear()
}