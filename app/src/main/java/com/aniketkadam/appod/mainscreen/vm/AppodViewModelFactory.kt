package com.aniketkadam.appod.mainscreen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aniketkadam.appod.mainscreen.data.Repository
import javax.inject.Inject

class AppodViewModelFactory @Inject constructor(private val repository: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(MainVm::class.java) -> MainVm(repository)
            else -> throw IllegalArgumentException("what")
        }
    } as T
}