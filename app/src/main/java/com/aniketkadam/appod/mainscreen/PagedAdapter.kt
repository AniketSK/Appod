package com.aniketkadam.appod.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.aniketkadam.appod.R
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.MainListApodItemBinding

class PagedAdapter(private val onItemClickedCallback: (Int) -> Unit) :
    PagedListAdapter<AstronomyPic, ApodViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val binding = DataBindingUtil.inflate<MainListApodItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.main_list_apod_item,
            parent,
            false
        )
        return ApodViewHolder(binding, onItemClickedCallback)
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AstronomyPic>() {
            override fun areItemsTheSame(oldItem: AstronomyPic, newItem: AstronomyPic): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: AstronomyPic, newItem: AstronomyPic): Boolean =
                oldItem.date == newItem.date

        }
    }
}