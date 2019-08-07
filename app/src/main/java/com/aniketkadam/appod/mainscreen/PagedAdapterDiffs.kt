package com.aniketkadam.appod.mainscreen

import androidx.recyclerview.widget.DiffUtil
import com.aniketkadam.appod.data.AstronomyPic

class PagedAdapterDiffs {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AstronomyPic>() {
            override fun areItemsTheSame(oldItem: AstronomyPic, newItem: AstronomyPic): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: AstronomyPic, newItem: AstronomyPic): Boolean =
                oldItem.date == newItem.date

        }
    }
}