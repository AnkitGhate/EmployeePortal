package com.ankitgh.employeeportal.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}