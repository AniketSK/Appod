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

    private val _selectedItemPosition = MutableLiveData<Int>()
    fun setItemSelectedPosition(position: Int) {
        _selectedItemPosition.value = position
    }

    val selectedItemPosition: LiveData<Int>
        get() = _selectedItemPosition
}