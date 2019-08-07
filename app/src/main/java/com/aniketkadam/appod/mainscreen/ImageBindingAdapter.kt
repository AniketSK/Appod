package com.aniketkadam.appod.mainscreen

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context)
                .load(imageUrl).fitCenter().centerCrop()
                .placeholder(CircularProgressDrawable(view.context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                })
                .into(view)
        }
    }
}