package com.aniketkadam.appod.mainscreen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aniketkadam.appod.mainscreen.data.ApodRequestDates
import com.aniketkadam.appod.mainscreen.data.PREFETCH_DISTANCE
import com.aniketkadam.appod.mainscreen.data.Repository
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate

class MainVm(private val repository: Repository) : ViewModel() {

    private val effectRelay = PublishRelay.create<Unit>()


    val repoResult by lazy {
        repository.getApodList()
    }

    val apodList = repoResult.data
    val networkState = repoResult.networkState

    private val _selectedPositionAndFragment = MutableLiveData<PositionFragment>()
    fun setItemSelectedPosition(positionFragment: PositionFragment) {
        _selectedPositionAndFragment.value = positionFragment
    }

    val selectedPositionAndFragment: LiveData<PositionFragment>
        get() = _selectedPositionAndFragment

    private val swipeRefreshTransformer: ObservableTransformer<Unit, RefreshLce> = ObservableTransformer { o ->
        o.observeOn(Schedulers.io())
            .switchMapMaybe {
                repository.getLatestAstronomyPic()
                    // Neither can we use Java optionals, nor does Room's Maybe only call complete when it emits an item, like it's suppsoed to.
                    //   Provide a shim to be able to tell if the item was present or not.
                    .map { SwipeRefresh(true, it.date) }
                    .defaultIfEmpty(
                        SwipeRefresh(
                            false,
                            LocalDate.now().minusDays(PREFETCH_DISTANCE).toString()
                        )
                    ) // If no items, get a full pre-fetch distance's worth
            }
            .filter { !(it.itemPresent && LocalDate.now().isEqual(LocalDate.parse(it.date))) }
            .map { it.date }
            .flatMap { endDate ->
                repository.getApodsFromRemote(ApodRequestDates(endDate, LocalDate.now().toString())).toObservable()
                    .doOnNext { data -> repository.saveData(data) }
                    .map<RefreshLce> { RefreshLce.Success }
                    .onErrorReturn { e -> RefreshLce.Error(e) }
                    .startWith(RefreshLce.Loading)
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sendRefreshEvent() {
        effectRelay.accept(Unit)
    }

    val refreshState = effectRelay.compose(swipeRefreshTransformer)

}

sealed class ActiveFragmentPosition {
    object LIST_FRAGMENT : ActiveFragmentPosition()
    object DETAIL_FRAGMENT : ActiveFragmentPosition()
}

data class PositionFragment(val fragment: ActiveFragmentPosition, val position: Int)

private data class SwipeRefresh(val itemPresent: Boolean, val date: String)
sealed class RefreshLce {
    object Loading : RefreshLce()
    object Success :
        RefreshLce() // This is a non-standard LCE because the case when content is actually available is handled internally.

    data class Error(val e: Throwable?) : RefreshLce()
}