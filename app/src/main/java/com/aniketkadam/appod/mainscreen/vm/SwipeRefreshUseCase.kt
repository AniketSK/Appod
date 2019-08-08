package com.aniketkadam.appod.mainscreen.vm

import com.aniketkadam.appod.mainscreen.data.ApodRequestDates
import com.aniketkadam.appod.mainscreen.data.PREFETCH_DISTANCE
import com.aniketkadam.appod.mainscreen.data.Repository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate

class SwipeRefreshUseCase(private val repository: Repository) {

    val swipeRefreshTransformer: ObservableTransformer<Unit, RefreshLce> = ObservableTransformer { o ->
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
            .publish {
                Observable.merge(
                    it.handleAlreadyPresent(),
                    it.handleUpdateRequired()
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun Observable<SwipeRefresh>.handleUpdateRequired(): Observable<RefreshLce> {
        return filter { !(it.itemPresent && LocalDate.now().isEqual(LocalDate.parse(it.date))) }
            .map { it.date }
            .flatMap { endDate ->
                repository.getApodsFromRemote(ApodRequestDates(endDate, LocalDate.now().toString())).toObservable()
                    .doOnNext { data -> repository.saveData(data) }
                    .map<RefreshLce> { RefreshLce.Success }
                    .onErrorReturn { e -> RefreshLce.Error(e) }
                    .startWith(RefreshLce.Loading)
            }
    }

    private fun Observable<SwipeRefresh>.handleAlreadyPresent(): Observable<RefreshLce> {
        return filter { (it.itemPresent && LocalDate.now().isEqual(LocalDate.parse(it.date))) }.map { RefreshLce.Success }
    }

}

private data class SwipeRefresh(val itemPresent: Boolean, val date: String)
