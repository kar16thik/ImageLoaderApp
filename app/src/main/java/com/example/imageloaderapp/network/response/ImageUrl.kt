package com.example.imageloaderapp.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageUrl (@SerializedName("small") @Expose var smallUrl: String,
                     @SerializedName("thumb") @Expose var thumbUrl: String)