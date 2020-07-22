@file:Suppress("DEPRECATION")

package com.ankitgh.employeeportal.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
object NetworkUtil {

    fun isNetworkConnected(context : Context): Boolean {
        var result = false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }
}
