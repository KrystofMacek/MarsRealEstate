package com.example.android.marsrealestate

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgSrcUrl: String?){
    imgSrcUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
                .load(imgUri)
                // Options to display when img is not available
                .apply(RequestOptions()
                        // When the img is not yet available
                        .placeholder(R.drawable.loading_animation)
                        // When there was an error getting the img
                        .error(R.drawable.ic_broken_image))
                .into(imageView)
    }
}


@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    when(status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

    }
}