/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.utils

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

fun showSnackBar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
    val snackBar: Snackbar = Snackbar.make(view, message, length)
        .setTextColor(ContextCompat.getColor(view.context, R.color.dark_default_color_on_surface))
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorAccent))
    snackBar.show()
}
