package com.aniketkadam.appod.mainscreen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aniketkadam.appod.mainscreen.data.Repository

class AppodViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(MainVm::class.java) -> MainVm(repository)
            else -> throw IllegalArgumentException("${modelClass.name} is not a known ViewModel to ${AppodViewModelFactory::class.java.name}")
        }
    } as T
}