package com.example.android.marsrealestate

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgSrcUrl: String?){
    imgSrcUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
                .load(imgUri)
                .into(imageView)
    }
}