package com.aniketkadam.appod.mainscreen.data

import org.joda.time.LocalDate

/**
 * We need to get a certain number of items.
 * However, apod takes dates.
 * This class is responsible for giving a function that takes a number, which is the number
 * of items required.
 * And returns, a start and end date for them.
 * You may choose to get them from:
 *              'today'
 *              or a given date.
 *
 */
class ApodRequestConstructor {

    fun getDatesForNumItemsStartingToday(numItems: Int, today: LocalDate = LocalDate.now()): ApodRequestDates {
        val numDaysAgo = today.minusDays(numItems - 1)
        return ApodRequestDates(numDaysAgo.toString(), today.toString())
    }
}

class ApodRequestDates() : HashMap<String, String>() {

    constructor(startDate: String, endDate: String) : this() {
        put("start_date", startDate)
        put("end_date", endDate)
    }
}