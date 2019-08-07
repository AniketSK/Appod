package com.aniketkadam.appod.mainscreen

import androidx.recyclerview.widget.RecyclerView
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.DetailListApodItemBinding

class ApodDetailViewHolder(
    private val binding: DetailListApodItemBinding,
    private val onItemClickedCallback: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(incomingData: AstronomyPic) =
        with(binding) {
            data = incomingData
            root.setOnClickListener { onItemClickedCallback(adapterPosition) }
            executePendingBindings()
        }

}