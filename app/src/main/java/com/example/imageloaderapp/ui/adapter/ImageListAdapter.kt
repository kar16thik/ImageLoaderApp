package com.example.imageloaderapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloader.ImageLoader
import com.example.imageloaderapp.R
import com.example.imageloaderapp.databinding.AdapterImageViewBinding
import com.example.imageloaderapp.network.response.ImageResponse

class ImageListAdapter  ( val onItemSelect:(imageResponseData: ImageResponse)->Unit)
    : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {


    var mItemsList = ArrayList<ImageResponse>()
    lateinit var mContext:Context
    override fun getItemCount(): Int {
        return mItemsList.size
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterImageViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        mContext=parent.context
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var mImageResponse = mItemsList.get(position)

        with(holder) {

            binding.root.setOnClickListener {
                onItemSelect(mItemsList.get(position))
            }
            ImageLoader.with(mContext)
                .load(mImageResponse.imageUrl.thumbUrl)
                .error(R.drawable.no_image_chat)
                .into(binding.image)

        }
    }

    fun updateArticleData(articleList: ArrayList<ImageResponse>) {
        mItemsList = articleList
        notifyDataSetChanged()
    }

    fun clearData() {
        mItemsList.clear()
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: AdapterImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}