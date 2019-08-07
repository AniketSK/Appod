package com.aniketkadam.appod.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.aniketkadam.appod.R
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.MainListApodItemBinding

class PagedListAdapter(private val onItemClickedCallback: (Int) -> Unit) :
    PagedListAdapter<AstronomyPic, ApodListViewHolder>(PagedAdapterDiffs.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodListViewHolder {

        return ApodListViewHolder(
            DataBindingUtil.inflate<MainListApodItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.main_list_apod_item,
                parent,
                false
            ), onItemClickedCallback
        )
    }

    override fun onBindViewHolder(holder: ApodListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}