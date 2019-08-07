package com.aniketkadam.appod.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.di.MAIN_FRAGMENT_VM
import com.aniketkadam.appod.mainscreen.vm.ActiveFragmentPosition
import com.aniketkadam.appod.mainscreen.vm.MainVm
import com.aniketkadam.appod.mainscreen.vm.PositionFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class ListFragment : DaggerFragment() {
    @Inject
    @field:Named(MAIN_FRAGMENT_VM)
    lateinit var mainVm: MainVm
    val args by navArgs<ListFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagedListAdapter {
            mainVm.setItemSelectedPosition(
                PositionFragment(
                    ActiveFragmentPosition.DETAIL_FRAGMENT,
                    it
                )
            )
        }
        gridRecyclerView.layoutManager = GridLayoutManager(context, 3)
        gridRecyclerView.adapter = adapter

        mainVm.apodList.observe(this, Observer { adapter.submitList(it) })
        gridRecyclerView.scrollToPosition(args.adapterPosition)
    }
}