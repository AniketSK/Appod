package com.aniketkadam.appod.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.aniketkadam.appod.R
import com.aniketkadam.appod.data.AstronomyPic
import com.aniketkadam.appod.databinding.MainListApodItemBinding

const val LIST_FRAGMENT_VIEW_TYPE = 1
const val DETAIL_FRAGMENT_VIEW_TYPE = 2

class PagedAdapter(private val viewType: Int, private val onItemClickedCallback: (Int) -> Unit) :
    PagedListAdapter<AstronomyPic, ApodViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val binding = DataBindingUtil.inflate<MainListApodItemBinding>(
            LayoutInflater.from(parent.context),
            getIntendedView(viewType),
            parent,
            false
        )
        return ApodViewHolder(binding, onItemClickedCallback)
    }

    private fun getIntendedView(viewType: Int): Int = when (viewType) {
        LIST_FRAGMENT_VIEW_TYPE -> R.layout.main_list_apod_item
        DETAIL_FRAGMENT_VIEW_TYPE -> R.layout.detail_list_apod_item
        else -> throw IllegalArgumentException("Unknown view type")
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
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