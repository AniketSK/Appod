package com.aniketkadam.appod.mainscreen.apodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.aniketkadam.appod.R
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.MainListApodItemBinding
import com.aniketkadam.appod.mainscreen.PagedAdapterDiffs

class PagedListAdapter(private val openDetailView: (Int) -> Unit) :
    PagedListAdapter<AstronomyPic, ApodListViewHolder>(PagedAdapterDiffs.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodListViewHolder {

        return ApodListViewHolder(
            DataBindingUtil.inflate<MainListApodItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.main_list_apod_item,
                parent,
                false
            ), openDetailView
        )
    }

    override fun onBindViewHolder(holder: ApodListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}