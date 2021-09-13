package com.example.imageloaderapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imageloader.ImageLoader
import com.example.imageloaderapp.R
import com.example.imageloaderapp.databinding.ActivityImageFullBinding
import com.example.imageloaderapp.databinding.ActivityMainBinding
import com.example.imageloaderapp.ui.BINDEL_KEY_URL

class ImageFullActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityImageFullBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityImageFullBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initUi()
    }

    fun initUi(){
        var bundle = intent.extras
        if(bundle!=null && bundle.containsKey(BINDEL_KEY_URL)){
            val imageUrl=bundle.getString(BINDEL_KEY_URL,"")

            ImageLoader.with(this)
                .load(imageUrl)
                .error(R.drawable.no_image_chat)
                .into(mBinding.imageFull)

        }

    }
}