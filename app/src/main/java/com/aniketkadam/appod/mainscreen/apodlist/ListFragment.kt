package com.aniketkadam.appod.mainscreen.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.di.MAIN_FRAGMENT_VM
import com.aniketkadam.appod.mainscreen.vm.*
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_layout.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class ListFragment : DaggerFragment() {
    @Inject
    @field:Named(MAIN_FRAGMENT_VM)
    lateinit var mainVm: MainVm
    val args by navArgs<ListFragmentArgs>()
    var disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = getAdapter()
        gridRecyclerView.layoutManager = GridLayoutManager(context, 3)
        gridRecyclerView.adapter = adapter

        mainVm.apodList.observe(this, Observer {
            triggerEmptyView(!it.isEmpty())
            adapter.submitList(it)
        })

        gridRecyclerView.scrollToPosition(args.adapterPosition)

        swipeRefreshView.setOnRefreshListener { mainVm.sendRefreshEvent() }

        disposable.addAll(
            mainVm.refreshState.subscribe { renderSwipeRefresh(it) },
            mainVm.refreshEffects.subscribe { showErrorEffect() } // There's only one effect right now
        )
    }

    private fun getAdapter(): PagedListAdapter = PagedListAdapter {
        mainVm.setItemSelectedPosition(
            PositionFragment(
                ActiveFragmentPosition.DETAIL_FRAGMENT,
                it
            )
        )
    }

    private fun triggerEmptyView(emptyViewVisible: Boolean) {
        if (emptyViewVisible) {
            emptyView.visibility = View.GONE
            gridRecyclerView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.VISIBLE
            gridRecyclerView.visibility = View.GONE
        }
    }

    private fun showErrorEffect() {
        Toast.makeText(this.context, R.string.internet_error, Toast.LENGTH_LONG).show()
    }

    private fun renderSwipeRefresh(state: ViewState?): Unit = when (state?.refreshing) {
        is RefreshLce.Loading -> swipeRefreshView.isRefreshing = true
        is RefreshLce.Success -> swipeRefreshView.isRefreshing = false
        is RefreshLce.Error -> swipeRefreshView.isRefreshing = false
        else -> Timber.d("Got an empty on the swipe refreshState view")
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}