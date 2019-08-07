package com.aniketkadam.appod.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = PagedAdapter(DETAIL_FRAGMENT_VIEW_TYPE) {
            mainVm.setItemSelectedPosition(
                PositionFragment(
                    ActiveFragmentPosition.LIST_FRAGMENT,
                    it
                )
            )
        }
        gridRecyclerView.adapter = adapter
        mainVm.apodList.observe(this, Observer { adapter.submitList(it) })
    }
}