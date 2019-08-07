package com.aniketkadam.appod.mainscreen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.apoddetail.ApodDetailViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun launches() {
        activityTestRule.launchActivity(null)

    }

    @Test
    fun when_an_item_on_the_main_list_is_clicked_it_opens_the_detail_view_for_that_item() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("Sunset Analemma")), click()
            )
        )
        onView(withContentDescription("Sunset Analemma")).check(matches(isDisplayed()))
        onView(withText("Sunset Analemma")).check(matches(isDisplayed()))
    }

}