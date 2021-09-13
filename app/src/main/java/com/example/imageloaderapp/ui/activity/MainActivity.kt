package com.example.imageloaderapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imageloaderapp.ui.adapter.ImageListAdapter
import com.example.imageloaderapp.databinding.ActivityMainBinding
import com.example.imageloaderapp.ui.BINDEL_KEY_URL
import com.example.imageloaderapp.viewmodel.ImageListViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

     private lateinit var mBinding: ActivityMainBinding
     private val mImageListViewModel:ImageListViewModel by inject()

     private lateinit var mImageListAdapter:ImageListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initUi()
        mImageListViewModel.initImageList()
    }

    fun initUi(){
        initImageAdapter()
        getDataStreem()

    }

    fun initImageAdapter(){
        mImageListAdapter= ImageListAdapter(onItemSelect={
            gotoImageFullPage(it.imageUrl.smallUrl)
        })
        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        mBinding.rvImage.layoutManager = layoutManager
        mBinding.rvImage.adapter=mImageListAdapter
    }

    fun gotoImageFullPage(url:String){
        val intent = Intent(this, ImageFullActivity::class.java)
        intent.putExtra(BINDEL_KEY_URL, url)
        startActivity(intent)

    }
    private fun getDataStreem(){
        mImageListViewModel.getImageListLiveData().observe(this,{
            it?.let {
                if (it.size > 0) {
                    mImageListAdapter.updateArticleData(it)
                }
            }
        })
        mImageListViewModel.getErrorLiveData().observe(this,{
            it?.let {
                Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}