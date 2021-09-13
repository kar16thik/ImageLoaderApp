package com.example.imageloaderapp.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageResponse (@SerializedName("id") @Expose var id: String,
                     @SerializedName("urls") @Expose var imageUrl: ImageUrl)