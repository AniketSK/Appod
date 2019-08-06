package com.aniketkadam.appod.mainscreen.vm

import androidx.lifecycle.ViewModel
import com.aniketkadam.appod.mainscreen.data.Repository

class MainVm(private val repository: Repository) : ViewModel() {

    val repoResult by lazy {
        repository.getApodList()
    }

    val apodList = repoResult.data
    val networkState = repoResult.networkState


}