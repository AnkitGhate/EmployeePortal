package com.ankitgh.employeeportal.common

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotEmpty() && this.length >= 6
}

