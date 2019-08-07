package com.aniketkadam.appod.mainscreen.apoddetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.aniketkadam.appod.R
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.DetailListApodItemBinding
import com.aniketkadam.appod.mainscreen.PagedAdapterDiffs

class PagedDetailAdapter(private val onItemClickedCallback: (Int) -> Unit) :
    PagedListAdapter<AstronomyPic, ApodDetailViewHolder>(PagedAdapterDiffs.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodDetailViewHolder {
        return ApodDetailViewHolder(
            DataBindingUtil.inflate<DetailListApodItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.detail_list_apod_item,
                parent,
                false
            ), onItemClickedCallback
        )
    }

    override fun onBindViewHolder(holder: ApodDetailViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}