package com.ankitgh.employeeportal.data.model.firestoremodel

import android.net.Uri

data class UserSchema(
    var username: String = "",
    var designation: String = "",
    var password: String = "",
    var email: String = "",
    var photoUri: Uri? = null,
    var employeeid: String = "",
    var isSignUpComplete: Boolean = false
)