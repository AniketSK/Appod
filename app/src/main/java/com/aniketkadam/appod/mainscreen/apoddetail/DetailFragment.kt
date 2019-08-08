package com.aniketkadam.appod.mainscreen.apoddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.di.MAIN_FRAGMENT_VM
import com.aniketkadam.appod.mainscreen.vm.ActiveFragmentPosition
import com.aniketkadam.appod.mainscreen.vm.MainVm
import com.aniketkadam.appod.mainscreen.vm.PositionFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class DetailFragment : DaggerFragment() {
    @Inject
    @field:Named(MAIN_FRAGMENT_VM)
    lateinit var mainVm: MainVm
    val args by navArgs<DetailFragmentArgs>()
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        gridRecyclerView.layoutManager = layoutManager
        // Snap each linear item into the center when scrolling.
        LinearSnapHelper().apply { attachToRecyclerView(gridRecyclerView) }

        val adapter = getAdapter()
        gridRecyclerView.adapter = adapter
        mainVm.apodList.observe(this, Observer { adapter.submitList(it) })
        gridRecyclerView.scrollToPosition(args.adapterPosition)

        requireActivity().onBackPressedDispatcher.addCallback(this, getBackPressedCallback())
    }

    private fun getAdapter(): PagedDetailAdapter = PagedDetailAdapter {
        mainVm.setItemSelectedPosition(
            PositionFragment(
                ActiveFragmentPosition.LIST_FRAGMENT,
                it
            )
        )
    }

    private fun getBackPressedCallback(): OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            mainVm.setItemSelectedPosition(
                PositionFragment(
                    ActiveFragmentPosition.LIST_FRAGMENT,
                    layoutManager.findFirstVisibleItemPosition()
                )
            )
        }
    }

}