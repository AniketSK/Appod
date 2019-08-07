package com.aniketkadam.appod.mainscreen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aniketkadam.appod.mainscreen.data.Repository

class MainVm(private val repository: Repository) : ViewModel() {

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
}

sealed class ActiveFragmentPosition {
    object LIST_FRAGMENT : ActiveFragmentPosition()
    object DETAIL_FRAGMENT : ActiveFragmentPosition()
}

data class PositionFragment(val fragment: ActiveFragmentPosition, val position: Int)