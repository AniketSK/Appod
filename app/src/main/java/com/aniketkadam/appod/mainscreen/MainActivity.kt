package com.aniketkadam.appod.mainscreen

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.apoddetail.DetailFragmentDirections
import com.aniketkadam.appod.mainscreen.apodlist.ListFragmentDirections
import com.aniketkadam.appod.mainscreen.di.MAIN_VM
import com.aniketkadam.appod.mainscreen.vm.ActiveFragmentPosition
import com.aniketkadam.appod.mainscreen.vm.MainVm
import com.aniketkadam.appod.mainscreen.vm.PositionFragment
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    @field:Named(MAIN_VM)
    lateinit var mainVm: MainVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainVm.selectedPositionAndFragment.observe(this, Observer { setActive(it) })
    }

    private fun setActive(positionFragment: PositionFragment?) = with(findNavController(R.id.nav_host)) {
        when (positionFragment?.fragment) {
            ActiveFragmentPosition.LIST_FRAGMENT -> safeNavigate(
                DetailFragmentDirections.actionDetailFragmentToListFragment().setAdapterPosition(
                    positionFragment.position
                )
            )
            ActiveFragmentPosition.DETAIL_FRAGMENT -> safeNavigate(
                ListFragmentDirections.actionListFragmentToDetailFragment().setAdapterPosition(
                    positionFragment.position
                )
            )
            else -> throw IllegalArgumentException("Unknown Navigation Destiation")
        }
    }

}

private fun NavController.safeNavigate(d: NavDirections) =
    currentDestination?.getAction(d.actionId)?.let { navigate(d) }
        ?: Timber.e("Invalid route for direction ${d} with id ${d.actionId}")