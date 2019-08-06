package com.aniketkadam.appod.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.di.MAIN_FRAGMENT_VM
import com.aniketkadam.appod.mainscreen.vm.MainVm
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class ListFragment : DaggerFragment() {
    @Inject
    @Named(MAIN_FRAGMENT_VM)
    lateinit var mainVm: MainVm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_layout, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagedAdapter()
        gridRecyclerView.adapter = adapter

        mainVm.apodList.observe(this, Observer { adapter.submitList(it) })
    }
}