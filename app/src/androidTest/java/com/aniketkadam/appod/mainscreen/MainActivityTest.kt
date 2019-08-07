package com.aniketkadam.appod.mainscreen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.aniketkadam.appod.R
import com.aniketkadam.appod.mainscreen.apoddetail.ApodDetailViewHolder
import com.aniketkadam.appod.mainscreen.apodlist.ApodListViewHolder
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
            RecyclerViewActions.actionOnItem<ApodListViewHolder>(
                hasDescendant(withContentDescription("Magellanic Galaxy NGC 55")), scrollTo()
            )
        )
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodListViewHolder>(
                hasDescendant(withContentDescription("Magellanic Galaxy NGC 55")), click()
            )
        )
        onView(withContentDescription("Magellanic Galaxy NGC 55")).check(matches(isDisplayed()))
        onView(withText("Magellanic Galaxy NGC 55")).check(matches(isDisplayed()))
    }


    @Test
    fun opening_a_detail_view_scrolling_then_going_back_opens_on_the_same_view_in_the_main_list() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("HDR: Earth's Circular Shadow on the Moon")),
                scrollTo()
            )
        )
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("HDR: Earth's Circular Shadow on the Moon")),
                click()
            )
        )
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("Birds During a Total Solar Eclipse")),
                scrollTo()
            )
        )
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("Birds During a Total Solar Eclipse")),
                click()
            )
        )

        onView(withContentDescription("Birds During a Total Solar Eclipse")).check(matches(isDisplayed()))
    }

    @Test
    fun opening_a_detail_view_scrolling_then_going_back_by_pressing_the_back_button_opens_on_the_same_view_in_the_main_list() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("HDR: Earth's Circular Shadow on the Moon")),
                scrollTo()
            )
        )
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("HDR: Earth's Circular Shadow on the Moon")),
                click()
            )
        )
        onView(withId(R.id.gridRecyclerView)).perform(
            RecyclerViewActions.actionOnItem<ApodDetailViewHolder>(
                hasDescendant(withContentDescription("Birds During a Total Solar Eclipse")),
                scrollTo()
            )
        )
        pressBack()

        onView(withContentDescription("Birds During a Total Solar Eclipse")).check(matches(isDisplayed()))
    }

}