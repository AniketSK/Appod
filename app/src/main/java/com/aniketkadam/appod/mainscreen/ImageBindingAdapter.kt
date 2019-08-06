package com.aniketkadam.appod.mainscreen

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

object ImageBindingAdapter {

    @BindingAdapter("imageUrl")
    fun bindImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(url).fitCenter()
                .placeholder(CircularProgressDrawable(view.context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                })
                .into(view)
        }
    }
}