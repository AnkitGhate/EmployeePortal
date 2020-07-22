package com.ankitgh.employeeportal.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.ankitgh.employeeportal.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

fun toggleHideyBar(uiOptions: Int): Int {

    // BEGIN_INCLUDE (get_current_ui_flags)
    // The UI options currently enabled are represented by a bitfield.
    // getSystemUiVisibility() gives us that bitfield.

    var newUiOptions = uiOptions
    // END_INCLUDE (get_current_ui_flags)
    // BEGIN_INCLUDE (toggle_ui_flags)
    val isImmersiveModeEnabled =
        uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
    if (isImmersiveModeEnabled) {
        Timber.d("Turning immersive mode mode off. ")
    } else {
        Timber.d("Turning immersive mode mode on.")
    }

    // Navigation bar hiding:  Backwards compatible to ICS.
    if (Build.VERSION.SDK_INT >= 14) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    // Status bar hiding: Backwards compatible to Jellybean
    if (Build.VERSION.SDK_INT >= 16) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    // Immersive mode: Backward compatible to KitKat.
    // Note that this flag doesn't do anything by itself, it only augments the behavior
    // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
    // all three flags are being toggled together.
    // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
    // Sticky immersive mode differs in that it makes the navigation and status bars
    // semi-transparent, and the UI flag does not get cleared when the user interacts with
    // the screen.
    if (Build.VERSION.SDK_INT >= 18) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    return newUiOptions
    // END_INCLUDE (set_ui_flags)
}

fun showSnackBar(view: View, message: String) {
    val snackBar: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .setTextColor(ContextCompat.getColor(view.context, R.color.white_50))
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorAccent))
    snackBar.show()
}
