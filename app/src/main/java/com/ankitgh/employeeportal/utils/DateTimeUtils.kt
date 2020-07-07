package com.ankitgh.employeeportal.utils

import android.text.format.DateUtils
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

fun getRelativeDateTimeFromString(datetime: String?): String {
    val ta: TemporalAccessor = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(datetime)
    val instant: Instant = Instant.from(ta)
    val date = Date.from(instant)
    return DateUtils.getRelativeTimeSpanString(date.time).toString()
}
