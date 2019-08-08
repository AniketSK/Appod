package com.aniketkadam.appod.mainscreen.apodlist

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
import com.aniketkadam.appod.mainscreen.vm.RefreshLce
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_layout.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class ListFragment : DaggerFragment() {
    @Inject
    @field:Named(MAIN_FRAGMENT_VM)
    lateinit var mainVm: MainVm
    val args by navArgs<ListFragmentArgs>()
    lateinit var d: Disposable

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

        Timber.d("List fragment started")
        swipeRefreshView.setOnRefreshListener { mainVm.sendRefreshEvent() }
        d = mainVm.refreshEffects.subscribe {
            Timber.d("received ${it.javaClass}")
            renderSwipeRefresh(it)
        }
    }

    private fun renderSwipeRefresh(state: RefreshLce?): Unit = when (state) {
        is RefreshLce.Loading -> swipeRefreshView.isRefreshing = true
        is RefreshLce.Success -> swipeRefreshView.isRefreshing = false
        is RefreshLce.Error -> swipeRefreshView.isRefreshing = false
        null -> Timber.d("Got an empty on the swipe refreshEffects view")
    }

    override fun onDestroy() {
        d.dispose()
        super.onDestroy()
    }
}