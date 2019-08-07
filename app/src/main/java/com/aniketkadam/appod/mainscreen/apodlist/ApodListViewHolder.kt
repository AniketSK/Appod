package com.aniketkadam.appod.mainscreen.apodlist

import androidx.recyclerview.widget.RecyclerView
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.MainListApodItemBinding

class ApodListViewHolder(
    private val binding: MainListApodItemBinding,
    private val onItemClickedCallback: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(incomingData: AstronomyPic) =
        with(binding) {
            data = incomingData
            root.setOnClickListener { onItemClickedCallback(adapterPosition) }
            executePendingBindings()
        }

}