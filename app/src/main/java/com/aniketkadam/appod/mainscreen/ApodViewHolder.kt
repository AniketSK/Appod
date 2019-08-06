package com.aniketkadam.appod.mainscreen

import androidx.recyclerview.widget.RecyclerView
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.MainListApodItemBinding

class ApodViewHolder(private val binding: MainListApodItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(incomingData: AstronomyPic) =
        with(binding) {
            data = incomingData
            executePendingBindings()
        }

}