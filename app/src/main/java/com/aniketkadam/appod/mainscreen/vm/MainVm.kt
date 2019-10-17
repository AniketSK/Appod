package com.aniketkadam.appod.mainscreen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aniketkadam.appod.mainscreen.data.Repository
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainVm(private val repository: Repository) : ViewModel() {

    private lateinit var disposeable: CompositeDisposable
    private val publishRelay = PublishRelay.create<Unit>()


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


    val refreshState: Observable<ViewState>
    val refreshEffects: Observable<RefreshLce>

    private fun Observable<RefreshLce>.toViewState(): Observable<ViewState> {
        return scan(ViewState()) { previousState: ViewState, refreshLce: RefreshLce ->
            previousState.copy(
                refreshing = refreshLce
            )
        }
    }

    private fun Observable<RefreshLce>.toViewEffects(): Observable<RefreshLce> {
        return filter { it is RefreshLce.Error }
    }

    fun sendRefreshEvent() {
        publishRelay.accept(Unit)
    }

    init {
        val swipeRefreshUseCase = SwipeRefreshUseCase(repository)
        publishRelay.compose(swipeRefreshUseCase.swipeRefreshTransformer)
            .share()
            .also { result ->
                refreshState = result
                    .toViewState()
                    .replay(1).autoConnect(1) { disposeable.add(it) }

                refreshEffects = result.toViewEffects()
            }
    }

    fun setBookmark(picId: String, isBookmark: Boolean) = repository.setIsBookmarkedAstronomyPic(picId, isBookmark)

    override fun onCleared() {
        super.onCleared()
        disposeable.dispose()
    }
}

data class ViewState(val refreshing: RefreshLce? = null)

sealed class ActiveFragmentPosition {
    object LIST_FRAGMENT : ActiveFragmentPosition()
    object DETAIL_FRAGMENT : ActiveFragmentPosition()
}

data class PositionFragment(val fragment: ActiveFragmentPosition, val position: Int)

sealed class RefreshLce {
    object Loading : RefreshLce()
    object Success :
        RefreshLce() // This is a non-standard LCE because the case when content is actually available is handled internally.
    data class Error(val e: Throwable?) : RefreshLce()
}